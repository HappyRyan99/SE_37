package org.softwareeng.group37.utils;

import java.io.File;

public class InitDatabase {

    public static void init() {
        File file = new File("users.csv");
        try {
            if (file.exists()) {
                file.createNewFile();
                LogUtils.DEBUG(InitDatabase.class.getName(), "Database created successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
