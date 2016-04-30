package com.newer.eshop.tools;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class Tools {

    public static String DateFormat (String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.valueOf(str));
        return format.format(date);
    }
}
