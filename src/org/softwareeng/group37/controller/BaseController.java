package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;

public class BaseController<T> {

    private BaseController() {
    }

    protected EntityDao<T> mBaseDao;
    java.util.Scanner mScanner = new java.util.Scanner(System.in);

    protected BaseController(String fileName, Class clazz) {
        mBaseDao = new EntityDao<T>(fileName, clazz);
    }

    public void finish() {
        mBaseDao.writeToFile();
        mBaseDao = null;
    }

}
