package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;

public class BaseController<T> {

    protected BaseController() {
    }

    protected EntityDao<T> mBaseDao;
    java.util.Scanner mScanner = new java.util.Scanner(System.in);

    public void finish() {
        if (mBaseDao != null) {
            mBaseDao.flush();
        }
        mBaseDao = null;
    }

}
