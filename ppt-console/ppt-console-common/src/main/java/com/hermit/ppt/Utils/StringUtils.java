package com.hermit.ppt.Utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtils {

    public static String buildUrl(String schema,String host,String path){
        try {
            URL url = new URL(schema,host,path);
            return url.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
