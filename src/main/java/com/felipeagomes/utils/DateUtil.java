package com.felipeagomes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date toDate(String dateString) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            return dateFormatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(format);

        return desiredFormat.format(date);
    }
}
