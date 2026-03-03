package org.softwareeng.group37.controller;

import org.softwareeng.group37.model.Teacher;

public class TeachingReqController extends BaseController {
    private final static String REQUIREMENT_FILE = "requirement.csv";

    protected TeachingReqController(String fileName, Class clazz) {
        super(fileName, clazz);
    }

    public TeachingReqController() {
        super(REQUIREMENT_FILE, Teacher.class);
    }
}
