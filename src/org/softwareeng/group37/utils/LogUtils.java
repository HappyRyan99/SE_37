package org.softwareeng.group37.utils;

public class LogUtils {

    static String LOGFORMAT = "%s , %s";

    public static void DEBUG(String TAG, String message) {
        System.out.println(String.format(LOGFORMAT, TAG, message));
    }

    public static void ERROR(String TAG, String message, Exception e) {
        System.out.println(String.format(LOGFORMAT, TAG, message));
        e.printStackTrace();
    }

}
