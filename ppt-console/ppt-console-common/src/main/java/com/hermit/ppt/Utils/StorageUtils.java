package com.hermit.ppt.Utils;

import java.util.HashMap;
import java.util.Map;

public class StorageUtils {
    private static Map<String,Long> sizes = new HashMap<String,Long>(){
        {
            put("KB",1024L);
            put("MB",1024 * 1024L);
            put("GB",1024 * 1024 * 1024L);
            put("TB",1024 * 1024 * 1024 * 1024L);
        }
    };


    public static long parseSize(String size){
        String str = size.toUpperCase();

        String unit = sizes.keySet().stream().filter(s -> str.contains(s)).findFirst().get();
        return Double.valueOf(Double.parseDouble(str.replace(unit,"")) * sizes.get(unit)).longValue();

    }

}
