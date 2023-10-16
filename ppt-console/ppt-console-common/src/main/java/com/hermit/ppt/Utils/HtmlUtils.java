package com.hermit.ppt.Utils;

public class HtmlUtils {

    public static String appendTable(String html){
        return String.format("%s%s%s","<table>",html,"</table>");
    }
}
