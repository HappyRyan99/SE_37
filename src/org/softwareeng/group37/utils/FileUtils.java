package org.softwareeng.group37.utils;

import java.io.IOException;

public class FileUtils {

    private final static String FILEUTILSTAG = FileUtils.class.getSimpleName();

    public static boolean fileWriteString(String mFileName, String data) {
        try (java.io.FileWriter fw = new java.io.FileWriter(mFileName, true)) {
            fw.append(data);
            fw.flush();
            return true;
        } catch (IOException e) {
            LogUtils.ERROR(FILEUTILSTAG, "Failed to write data to file: " + mFileName, e);
            return false;
        }
    }


}

