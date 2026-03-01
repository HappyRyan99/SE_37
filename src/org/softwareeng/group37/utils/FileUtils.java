package org.softwareeng.group37.utils;

import org.softwareeng.group37.model.Entity;

import java.io.IOException;

public class FileUtils {

    private final static String LOG_TAG = FileUtils.class.getSimpleName();

    public static boolean fileWrite(String mFileName, Object data) {
        try (java.io.FileWriter fw = new java.io.FileWriter(mFileName, true)) {
            if (data instanceof Entity) {
                String csvLine = ((Entity) data).toWrite();
                fw.append(csvLine);
                return true;
            } else {
                LogUtils.WARNING(LOG_TAG, "Data is not an instance of Entity.");
                return false;
            }
        } catch (IOException e) {
            LogUtils.ERROR(LOG_TAG, "Failed to write data to file: " + mFileName, e);
            return false;
        }
    }

    public static boolean fileWriteString(String mFileName, String data) {
        try (java.io.FileWriter fw = new java.io.FileWriter(mFileName, true)) {
            fw.append(data);
            return true;
        } catch (IOException e) {
            LogUtils.ERROR(LOG_TAG, "Failed to write data to file: " + mFileName, e);
            return false;
        }
    }


}

