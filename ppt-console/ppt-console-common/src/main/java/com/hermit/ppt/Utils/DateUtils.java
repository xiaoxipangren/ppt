package com.hermit.ppt.Utils;



import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    private final static Map<String, ChronoUnit> DATE_UNITS = new HashMap<String,ChronoUnit>(){
        {
            put("年",ChronoUnit.YEARS);
            put("月",ChronoUnit.MONTHS);
            put("周",ChronoUnit.WEEKS);
            put("日",ChronoUnit.DAYS);
            put("時",ChronoUnit.HOURS);
            put("时",ChronoUnit.HOURS);
            put("分",ChronoUnit.MINUTES);
        }
    };



    public static Duration pasrteDuration(String time){
        String[] array = time.split("");

        Duration duration = Duration.of(0,ChronoUnit.MILLIS);

        for(int i = 0; i< array.length; i++){
            int j = i;
            while(j< array.length && StringUtils.isNumeric(array[j])){
                j++;
            }
            String unit = array[j];
            duration = duration.plus(Duration.of(Integer.parseInt(time.substring(i,j)),DATE_UNITS.get(unit)));
            i = j;
        }

        return duration;
    }


}
