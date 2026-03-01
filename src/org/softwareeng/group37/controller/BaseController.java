package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.Skills;

public class BaseController<T> {

    private BaseController() {
    }

    protected EntityDao<?> baseDao;
    java.util.Scanner scanner = new java.util.Scanner(System.in);

    protected BaseController(String fileName, Class clazz) {
        baseDao = new EntityDao<T>(fileName, clazz);
    }

}
