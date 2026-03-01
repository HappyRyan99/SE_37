package org.softwareeng.group37.utils;

public class LogUtils {
    // ANSI color codes
    private static final String RESET  = "\u001B[0m";
    private static final String BLACK  = "\u001B[30m";
    private static final String RED    = "\u001B[31m";
    private static final String GREEN  = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE   = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN   = "\u001B[36m";
    private static final String WHITE  = "\u001B[37m";

    static String LOGFORMAT = "%s , %s";

    public static void DEBUG(String TAG, String message) {
        System.out.println(String.format(LOGFORMAT, TAG, message));
    }

    public static void ERROR(String TAG, String message, Exception e) {
        System.out.println(String.format(LOGFORMAT, TAG, message));
        e.printStackTrace();
    }

    public static void changeOutputColor(String colorName) {
        String code;
        switch (colorName.toUpperCase()) {
            case "BLACK":  code = BLACK;  break;
            case "RED":    code = RED;    break;
            case "GREEN":  code = GREEN;  break;
            case "YELLOW": code = YELLOW; break;
            case "BLUE":   code = BLUE;   break;
            case "PURPLE": code = PURPLE; break;
            case "CYAN":   code = CYAN;   break;
            case "WHITE":  code = WHITE;  break;
            default:       code = RESET;  // unknown color → reset
        }
        System.out.print(code);
    }

    public static void resetOutputColor() {
        System.out.print(RESET);
    }

}
