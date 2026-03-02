package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.dao.TeacherDAO;
import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.utils.DateUtils;
import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

import java.util.List;

import static org.softwareeng.group37.utils.LogUtils.*;

public class TeacherController {
    private final EntityDao<Teacher> teacherDao;
    private final static String TEACHER_FILE = "teacher.csv";
    java.util.Scanner scanner = new java.util.Scanner(System.in);

    public TeacherController() {
        teacherDao = new TeacherDAO(TEACHER_FILE);
    }

    public void showTeacherList() {
        List<Teacher> teachers = teacherDao.readAll();
        LogUtils.SUCCESS("Teacher List:");
        for (Teacher teacher : teachers) {
            LogUtils.changeOutputColor("CYAN");
            System.out.print("ID: ");
            LogUtils.changeOutputColor("GREEN");
            System.out.print(teacher.getId() + " ");

            LogUtils.changeOutputColor("CYAN");
            System.out.print("Name: ");
            LogUtils.changeOutputColor("YELLOW");
            System.out.print(teacher.getName() + " ");

            LogUtils.changeOutputColor("CYAN");
            System.out.print("Registration Date: ");
            LogUtils.changeOutputColor("BLUE");
            System.out.print(DateUtils.convertEpochToStringTime(teacher.getRegDate() )+ " ");

            LogUtils.changeOutputColor("CYAN");
            System.out.print("Status: ");
            LogUtils.changeOutputColor("PURPLE");
            System.out.print(teacher.getStatus() + "\n");

            LogUtils.resetOutputColor();
        }
    }

    public void showTeacherListShort() {
        List<Teacher> teachers = teacherDao.readAll();
        LogUtils.SUCCESS("Teacher List:");
        for (Teacher teacher : teachers) {
            LogUtils.changeOutputColor("CYAN");
            System.out.print("ID: ");
            LogUtils.changeOutputColor("GREEN");
            System.out.print(teacher.getId() + " ");

            LogUtils.changeOutputColor("CYAN");
            System.out.print("Name: ");
            LogUtils.changeOutputColor("YELLOW");
            System.out.print(teacher.getName() + " ");

            LogUtils.changeOutputColor("CYAN");
            System.out.print(":: ");
            LogUtils.changeOutputColor("PURPLE");
            System.out.print(teacher.getStatus() + "\t");

            LogUtils.resetOutputColor();
        }
    }

    public void addTeacher() {
        Teacher teacher = new Teacher();
        LogUtils.INFO("Teacher", "Add New Teacher");
        USERINPUT("enter teacher's name: ");
        String name = scanner.nextLine();
        teacher.setName(name);
        teacher.setRegDate(String.valueOf(System.currentTimeMillis()));
        teacher.setId(teacherDao.getANewId());
        teacher.setStatus(0);
        if (register(teacher)) {
            LogUtils.SUCCESS("Teacher added successfully: " + teacher.getName());
        }else {
            WARNING( "Teacher", "Failed to add teacher:");
        }
    }

    private boolean register(Teacher teacher) {
        return teacherDao.write(teacher);
    }

    public void train(){
        INFO( "Teacher", "Train Teacher");
        showTeacherListShort();
        int  id =-2;
        while (true) {
            try {
                USERINPUT("Enter Teacher ID: ");
                id = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }



    }


}
