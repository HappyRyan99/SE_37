package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.Requirement;
import org.softwareeng.group37.model.Teacher;

public class TeachingReqController {
    private final EntityDao<Requirement> teacherDao;
    private final static String REQUIREMENT_FILE = "requirement.csv";

    public TeachingReqController() {
        teacherDao = new EntityDao<>(REQUIREMENT_FILE, Teacher.class);
    }
}
