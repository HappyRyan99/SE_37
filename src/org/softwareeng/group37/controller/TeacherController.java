package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.utils.Utils;

import java.util.List;

public class TeacherController {
    private final EntityDao<Teacher> teacherDao;
    private final static String TEACHER_FILE = "teacher.csv";

    public TeacherController() {
        teacherDao = new EntityDao<>(TEACHER_FILE, Teacher.class);
    }

    public void addTeacher(Teacher teacher) {
        teacherDao.write(teacher);
    }

    public void showTeacherList() {
        List teachers = teacherDao.readAll();
        Utils.printScreen(teachers.toString());
    }

}
