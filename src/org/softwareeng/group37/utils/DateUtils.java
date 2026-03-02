package org.softwareeng.group37.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static String convertEpochToStringTime(String epoch) {
        long epochLong = Long.parseLong(epoch.trim());
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochLong), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
