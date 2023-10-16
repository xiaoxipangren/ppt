package com.hermit.ppt.spider;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.File;
import java.io.IOException;

public class MtTargetPageProcessorTest {

    @Test
    public void processPage() throws IOException {

        String html = FileUtils.readFileToString(new File("target.html"),"utf-8");
        Page page = new Page();
        page.setUrl(new PlainText("https://kp.m-team.cc/adult.php?inclbookmarked=0&incldead=1&spstate=0&page=0&sort=5&type=asc"));
        page.setHtml(new Html(html));

        MtTargetPageProcessor pageProcessor = new MtTargetPageProcessor();

        pageProcessor.processPage(page);

    }
}