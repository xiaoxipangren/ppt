package com.hermit.ppt.spider;

import com.hermit.ppt.entity.*;
import com.hermit.ppt.repository.*;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hermit.ppt.Utils.HtmlUtils.appendTable;

@Slf4j
@Component
public class MtTargetPageProcessor extends PatternProcessor {

    private final static String MT_TARGET_URL_PATTERN ="https://kp\\.m-team\\.cc/details.php\\?.*";


    @Autowired
    private DistroRepository distroRepository;

    @Autowired
    private TorrentRepository torrentRepository;

    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public MtTargetPageProcessor() {
        super(MT_TARGET_URL_PATTERN);
    }

    @Override
    @Transactional
    public MatchOther processPage(Page page) {

        Art art = null;
        Distro distro = new Distro();
        Artist artist = null;
        Series series = null;
        Vendor vendor = null;




        Html html = page.getHtml();
        String name = html.xpath("//h1/text()").get();
        art = getEntityByName(artRepository::findByName,name,Art::new);

        Map<String,Selectable> selectables = html.xpath("//td[@id=\"outer\"]/table/tbody/tr/outerHtml()")
                .all()
                .stream()
                .map(s -> new Html(appendTable(s)))
                .collect(Collectors.toMap(h -> h.xpath("//td[1]/allText()").get(),h-> new Html(appendTable(h.xpath("//td[2]/outerHtml()").get()))));

        Selectable s = selectables.get("下載");
        String url = s.xpath("//td//a[@class=\"index\"]/@href").get();
        Torrent torrent = torrentRepository.findByUrl(url);


        s = selectables.get("副標題");
        distro.setName(name);
        distro.setTitle(s.xpath("//td/allText()").get());

        s = selectables.get("演員");
        artist=getEntityByName(artistRepository::findByName,s.xpath("//td/allText()").get(),Artist::new);


        s = selectables.get("Tag");
        art.setTags(new HashSet<>(Arrays.asList(s.xpath("//td/allText()").get().split(","))));

        s = selectables.get("基本資訊");
        if(s!=null){
            final String[] text = {s.xpath("//td/allText()").get()};
            List<String> keys = s.xpath("//b/text()").all().stream().filter(t -> StringUtils.isNotBlank(t)).collect(Collectors.toList());
            keys.forEach(k -> text[0] = text[0].replace(k,"@"));
            List<String> values = Arrays.asList(text[0].split("@")).stream().filter(t -> StringUtils.isNotBlank(t)).collect(Collectors.toList());
            Map<String,String> maps=IntStream.rangeClosed(0,keys.size()-1).mapToObj(i -> i).collect(Collectors.toMap(i -> keys.get(i).replace(":","").replace("：","").replace(" ",""),i -> values.get(i)));
            String codec = maps.get("編碼");
            String resolution = maps.get("解析度");
            String region = maps.get("處理");
            boolean pack = maps.containsKey("制作組");
            distro.setCodec(codec);
            distro.setResolution(resolution);
            art.setRegion(region);
            torrent.setPack(pack);
        }


        s=selectables.get("簡介");
        List<String> snapshots = s.xpath("//img/@src").all();
        distro.getSnapshots().addAll(snapshots);

        s = selectables.get("DMM");
        if(s != null){

            String cover = s.xpath("//div[@class=\"dmm_type\"]/img/@src").get();
            art.setCover(cover);
            distro.getSnapshots().add(cover);

            Map<String,Selectable> maps = s.xpath("//tr/outerHtml()")
                    .all()
                    .stream()
                    .map(o -> new Html(appendTable(o)))
                    .collect(Collectors.toMap(h -> h.xpath("//td[1]/allText()").get().replace("：",""),h-> new Html(appendTable(h.xpath("//td[2]/outerHtml()").get()))));

            String releaseStr = maps.get("発売日").xpath("//td/allText()").get();
            art.setReleaseDate(LocalDate.parse(releaseStr, DateTimeFormatter.ofPattern("yyyy/MM/dd")));

            String lastStr = maps.get("収録時間").xpath("//td/allText()").get();
            art.setLast(Integer.parseInt(lastStr.replace("分","")));



            String seriesStr = maps.get("シリーズ").xpath("//td/allText()").get();
            if(StringUtils.isNotBlank(seriesStr)){
                series=getEntityByName(seriesRepository::findByName,seriesStr,Series::new);
                series.getArts().add(art);
            }

            String vendorStr = maps.get("メーカー").xpath("//td/allText()").get();
            vendor = getEntityByName(vendorRepository::findByName,vendorStr,Vendor::new);
            vendor.getArts().add(art);

            List<String> labels = maps.get("ジャンル").xpath("//a/allText()").all();
            Art finalArt = art;
            labels.forEach(la -> {
                Label label = getEntityByName(labelRepository::findByName,la,Label::new);
                finalArt.getLabels().add(label);
            });

            art.setCode(maps.get("品番").xpath("//td/allText()").get());
            distro.getSnapshots().addAll(s.xpath("//div[@class=\"dmm_pic\"]/img/@src").all());
            art.setDescription(s.xpath("//div[@class=\"dmm_text\"]/allText()").get());
        }

        s = selectables.get("種子檔案");
        torrent.setHash(s.xpath("//td[@class=\"no_border_wide\"]/allText()").all().stream().filter(t -> t.contains("Hash碼")).findFirst().get().replace("Hash碼:",""));


        art.setVendor(vendor);
//        vendor.getArts().add(art);

        art.setSeries(series);
//        series.getArts().add(art);


        distro.setArt(art);
//        art.getDistros().add(distro);

        distro.setTorrent(torrent);
//        torrent.getDistros().add(distro);

        art.getArtists().add(artist);

        distroRepository.save(distro);

        return MatchOther.NO;
    }


    @Override
    public MatchOther processResult(ResultItems resultItems, Task task) {
        return MatchOther.NO;
    }

    private <T extends BaseEntity> T getEntityByName(Function<String,T> selector, String name, Callable<T> builder){
        T entity = selector.apply(name);
        if(entity == null){
            try {
                entity = builder.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            entity.setName(name);
        }

        return entity;
    }


}
