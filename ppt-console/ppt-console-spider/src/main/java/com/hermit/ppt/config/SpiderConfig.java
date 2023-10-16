package com.hermit.ppt.config;

import com.hermit.ppt.Utils.StringUtils;
import com.hermit.ppt.spider.MtHelpPageProcessor;
import com.hermit.ppt.spider.MtTargetPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.handler.CompositePipeline;

@Configuration
public class SpiderConfig {


    @Autowired
    private SpiderProperty spiderProperty;

    @Bean
    public Spider spider(MtHelpPageProcessor helpPageProcessor,MtTargetPageProcessor targetPageProcessor){

        SpiderProperty.WebSite webSite = spiderProperty.getSites().get("mt");


        Site site = new Site();
        site.setDomain(webSite.getDomain());
        webSite.getCookies().forEach(site::addCookie);
        webSite.getHeaders().forEach(site::addHeader);

        CompositePageProcessor pageProcessor = new CompositePageProcessor(site);
        pageProcessor.addSubPageProcessor(helpPageProcessor).addSubPageProcessor(targetPageProcessor);

        CompositePipeline pipeline = new CompositePipeline();
        pipeline.addSubPipeline(helpPageProcessor).addSubPipeline(targetPageProcessor);

        return Spider.create(pageProcessor)
                .addPipeline(pipeline)
                .addUrl(StringUtils.buildUrl(webSite.getSchema(),webSite.getDomain(),webSite.getStart()));

    }

}
