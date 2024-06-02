package com.weijie.core.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MyDate {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Date getDate(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(Date d) {
        return sdf.format(d);
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }
}
