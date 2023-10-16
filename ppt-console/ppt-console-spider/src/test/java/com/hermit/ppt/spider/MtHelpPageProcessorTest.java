package com.hermit.ppt.spider;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class MtHelpPageProcessorTest {

    @Test
    public void processPage() throws IOException {

        String html = FileUtils.readFileToString(new File("help.html"),"utf-8");
        Page page = new Page();
        page.setUrl(new PlainText("https://kp.m-team.cc/adult.php?inclbookmarked=0&incldead=1&spstate=0&page=0&sort=5&type=asc"));
        page.setHtml(new Html(html));

        MtHelpPageProcessor pageProcessor = new MtHelpPageProcessor();

        pageProcessor.processPage(page);


    }
}