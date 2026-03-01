package org.softwareeng.group37.utils;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.User;

import java.util.Collections;
import java.util.Comparator;

public class Utils {

    public static void printScreen(String msg) {
        System.out.println(msg);
    }

    public static int generateNewUserId() {
//        EntityDao< User >
        EntityDao entityDao = new EntityDao("users.csv", User.class);
//        Integer maxInt = entityDao.getAllId().stream().max(Comparator.comparing(Integer::intValue)).get();
        System.out.println(entityDao.getAllId());
        return 99;
    }

}
