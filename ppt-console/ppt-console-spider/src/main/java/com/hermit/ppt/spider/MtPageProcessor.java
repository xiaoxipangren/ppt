package com.hermit.ppt.spider;

import com.hermit.ppt.Utils.DateUtils;
import com.hermit.ppt.entity.Torrent;
import com.hermit.ppt.enums.ArtType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MtPageProcessor implements PageProcessor {

    private final static String MT_LIST_URL_PATTERN="https://kp\\.m-team\\.cc/adult\\.php.*";
    private final static String MT_DETAIL_URL_PATTERN="https://kp\\.m-team\\.cc/details.php?id=\\n&hit=\\n";


    private

    @Override
    public void process(Page page) {

        if(page.getUrl().regex(MT_LIST_URL_PATTERN).match()){
            page.addTargetRequests(page.getHtml().links().regex(MT_DETAIL_URL_PATTERN).all());

            page.getHtml().xpath("//table[@class=\"torrents\"]//tr/outerHtml()").all().forEach(
                    h -> {
                        Html html = new Html(h);
                        if(html.xpath("/tr[@class=\"colhead\"]").match()){
                            return;
                        }
                        html.xpath("").



                    }

            );


        }
    }




    private void extraRelease(Selectable selectable,Torrent torrent){
        String releaseString = selectable.xpath("//span/@title").get();
        LocalDateTime release = LocalDateTime.parse(releaseString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        torrent.setUploadTime(release);
    }

    private void extraTorrent(Selectable selectable, Torrent torrent){
        String title = selectable.xpath("//td[@class=\"torrentimg\"]/a/@title").get();
        torrent.setName(title);

        String cover = selectable.xpath("//td[@class=\"torrentimg\"]//img/@src").get();
        torrent.setCover(cover);

        String artUrl = selectable.xpath("//td[@class=\"torrentimg\"]/a/@href").get();


        boolean free = selectable.xpath("//img[@class=pro_free]").match();
        torrent.setFree(free);
        if(free){
            String freeString = selectable.xpath("//span[@class=\"ont-weight:normal\"]/text()").get();
            LocalDateTime freeEnd = freeEnd(freeString);
            torrent.setFreeEnd(freeEnd);
        }

        String url = selectable.xpath("//img[@title=\"下载本种\"]/../a/@href").get();
        torrent.setUrl(url);



    }

    private LocalDateTime freeEnd(String freeString){
        if(StringUtils.isNotBlank(freeString)){
            LocalDateTime now = LocalDateTime.now();
            return  now.plus(DateUtils.pasrteDuration(freeString));

        }else {
            return LocalDateTime.MAX;
        }
    }



    private ArtType extraType(Selectable selectable){
        String type = extra(selectable,"//img/@title");
        ArtType artType = null;
        if(StringUtils.isNotBlank(type)){
            type = type.split("/")[1].replace(" ","_").toUpperCase();
            try{
                artType = ArtType.valueOf(type);
            }
            catch (Exception e){
                log.warn("Can't parse arttype from {}.",type);
            }
        }

        return artType;

    }

    private String extra(Selectable selectable,String xpath){
        return selectable.xpath(xpath).get();
    }

}
