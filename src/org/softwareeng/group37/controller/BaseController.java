package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;

public class BaseController<T> {

    private BaseController() {
    }

    protected EntityDao<T> mBaseDao;
    java.util.Scanner mScanner = new java.util.Scanner(System.in);

    protected BaseController(Class clazz) {

    }

    public void finish() {
        if (mBaseDao != null) {
            mBaseDao.writeToFile();
        }
        mBaseDao = null;
    }

}
