package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.utils.LogUtils;

import java.util.List;

public class TeacherDAO extends EntityDao<Teacher> {
    public TeacherDAO() {
        super("teacher.csv", Teacher.class);
    }

    public TeacherDAO(String fileName) {
        super(fileName, Teacher.class);
    }


    @Override
    public int add(Teacher data) {
        try {
            if (data == null) {
                LogUtils.WARNING(getClass().getName(), "Teacher is null");
                return -1;
            }
//             for data initialization
            if (data.getId() == -1) {
                return super.add(data);
            }
            if (data.getRegDate().isBlank()) {
                data.setRegDate(String.valueOf(System.currentTimeMillis()));
            }
            if (data.getId() == 0 || data.getId() < -1) {
                data.setId(getANewId());
            }
            if (data.getName().isBlank()) {
                LogUtils.WARNING(getClass().getName(), "Teacher name is empty");
                return -1;
            }
            List<Teacher> teachers = readByField("name", data.getName());
            if (!teachers.isEmpty()) {
                LogUtils.WARNING(getClass().getName(), "Teacher name already exists");
                return -1;
            }
            return super.add(data);
        } catch (Exception e) {
            LogUtils.ERROR(getClass().getName(), "Error writing data: ", e);
            return -1;
        }
    }
}
