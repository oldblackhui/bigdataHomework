package com.cumt.bigdata;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: ahui
 * @date: 2021/12/28 - 21:41
 */
public class timetest {
    public static void main(String[] args) throws ParseException {
        String d1 = "2021-12-11 10:50";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = df.parse(d1);

        System.out.println();
        Date date = new Date(parse.getTime() + (long) 60 * 1000);
        String format = df.format(date);
        System.out.println("format:" +format);
        String string = date.toString();
        System.out.println(string);
    }
}
