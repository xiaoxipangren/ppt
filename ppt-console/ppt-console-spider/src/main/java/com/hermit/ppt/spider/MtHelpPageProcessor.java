package com.hermit.ppt.spider;

import com.hermit.ppt.Utils.DateUtils;
import com.hermit.ppt.Utils.StorageUtils;
import com.hermit.ppt.entity.Torrent;
import com.hermit.ppt.repository.TorrentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.handler.PatternProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

import static com.hermit.ppt.Utils.HtmlUtils.appendTable;

@Slf4j
@Component
public class MtHelpPageProcessor extends PatternProcessor {

    private final static String MT_HELP_URL_PATTERN ="https://kp\\.m-team\\.cc/adult\\.php.*";
    private final static String MT_TARGET_URL_PATTERN ="https://kp\\.m-team\\.cc/details.php\\?.*";

    @Autowired
    private TorrentRepository torrentRepository;

    public MtHelpPageProcessor() {
        super(MT_HELP_URL_PATTERN);
    }


    @Override
    public MatchOther processPage(Page page) {

        page.addTargetRequests(page.getHtml().links().regex(MT_TARGET_URL_PATTERN).all());

        List<Torrent> torrents = page.getHtml().xpath("//table[@class=\"torrents\"]/tbody/tr").all().stream().map(
                h -> {
                    Html html = new Html(appendTable(h));
                    if(html.xpath("//td[@class=\"colhead\"]").match()){
                        return null;
                    }
                    Torrent torrent = new Torrent();
                    List<Selectable> selectables = html.xpath("//body/table/tbody/tr/td/outerHtml()")
                            .all()
                            .stream()
                            .map(s -> new Html(appendTable(s)))
                            .collect(Collectors.toList());

                    extraType(selectables.get(0),torrent);
                    extraTorrent(selectables.get(1),torrent);
                    extraUploadTime(selectables.get(3),torrent);
                    extraSize(selectables.get(4),torrent);
                    extraCount(selectables.get(5), torrent::setSeedings);
                    extraCount(selectables.get(6),torrent::setDownloadings);
                    extraCount(selectables.get(7),torrent::setDownloadeds);
                    extraDownloaded(selectables.get(8),torrent);
                    log.info("Extra torrent:  {}.",torrent);
                    return torrent;
                }
        ).filter(t -> t != null).collect(Collectors.toList());

        page.putField("torrents",torrents);

        return MatchOther.NO;
    }

    @Override
    @Transactional
    public MatchOther processResult(ResultItems resultItems, Task task) {

        List<Torrent> torrents = resultItems.get("torrents");

        if(torrents != null){
            torrentRepository.saveAll(torrents);
            log.info("Save {} torrents into db.",torrents.size());
        }

        return MatchOther.NO;
    }

    private void extraDownloaded(Selectable selectable,Torrent torrent){
        String progress = selectable.xpath("//td/allText()").get();
        torrent.setDownloaded(!"--".equals(progress));
    }

    private void extraCount(Selectable selectable , IntConsumer consumer){
        int count = Integer.parseInt(selectable.xpath("//td/allText()").get().replaceAll(",",""));

        consumer.accept(count);
    }

    private void extraSize(Selectable selectable,Torrent torrent){
        String sizeString = selectable.xpath("//td/allText()").get().replaceAll(",","");
        torrent.setSize(StorageUtils.parseSize(sizeString));
    }

    private void extraUploadTime(Selectable selectable, Torrent torrent){
        String releaseString = selectable.xpath("//span/@title").get();
        LocalDateTime release = LocalDateTime.parse(releaseString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        torrent.setUploadTime(release);
    }

    private void extraTorrent(Selectable selectable, Torrent torrent){
        String title = selectable.xpath("//td[@class=\"torrentimg\"]/a/@title").get();
        torrent.setName(title);

        String cover = selectable.xpath("//td[@class=\"torrentimg\"]//img/@src").get();
        torrent.setCover(cover);

        if(selectable.xpath("//img[@class=\"pro_50pctdown\"]").match()){
            torrent.setHalf(true);
        }

        boolean free = selectable.xpath("//img[@class=pro_free]").match();
        torrent.setFree(free);
        if(free){
            String freeString = selectable.xpath("//span[@style=\"font-weight:normal\"]/text()").get();
            if(freeString.contains("：")){
                freeString = freeString.split("：")[1];
            }
            LocalDateTime freeEnd = parseDate(freeString);
            torrent.setFreeEnd(freeEnd);
        }

        String url = selectable.xpath("//td[@valign=\"middle\"]/a/@href").get();
        torrent.setUrl(url);
    }


    private void extraType(Selectable selectable, Torrent torrent){
        String type = extra(selectable,"//img/@title");
        torrent.setType(type);

    }

    private LocalDateTime parseDate(String dateString){
        if(StringUtils.isNotBlank(dateString)){
            LocalDateTime now = LocalDateTime.now();
            return  now.plus(DateUtils.pasrteDuration(dateString));

        }else {
            return LocalDateTime.MAX;
        }
    }


    private String extra(Selectable selectable,String xpath){
        return selectable.xpath(xpath).get();
    }


}
