package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.dao.TeacherDAO;
import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.utils.DateUtils;
import org.softwareeng.group37.utils.LogUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.softwareeng.group37.utils.LogUtils.*;

public class TeacherController extends BaseController<Teacher> {
    SkillController skillController = new SkillController();

    public TeacherController() {
        mBaseDao = TeacherDAO.getInstance();
    }

    public void showTeacherList() {
        List<Teacher> teachers = mBaseDao.queryAll();
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
            System.out.print(DateUtils.convertEpochToStringTime(teacher.getRegDate()) + " ");

            LogUtils.changeOutputColor("CYAN");
            System.out.print("Status: ");
            LogUtils.changeOutputColor("PURPLE");
            System.out.print(teacher.getStatus() + "\n");

            LogUtils.resetOutputColor();
        }
    }

    public void showTeacherDetailsShort(Teacher teacher) {
        LogUtils.changeOutputColor("CYAN");
        System.out.print("Teacher ID: ");
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

    public void showTeacherDetailsShort() {
        LogUtils.SUCCESS("Teacher List:");
        List<Teacher> teachers = mBaseDao.queryAll();
        for (Teacher teacher : teachers) {
            showTeacherDetailsShort(teacher);
        }
    }

    public void showTeacher(int teacherId) {
        Optional<Teacher> teacher = mBaseDao.read(teacherId);
        if (teacher.isPresent()) {
            showTeacherDetailsShort(teacher.get());
        } else {
            WARNING("Teacher", "Teacher not found");
        }
    }

    public void showTeacherSHort(int teacherId) {
        Optional<Teacher> teacher = mBaseDao.read(teacherId);
        if (teacher.isPresent()) {
            showTeacherDetailsShort(teacher.get());
        } else {
            WARNING("Teacher", "Teacher not found");
        }
    }

    public void addTeacher() {
        Teacher teacher = new Teacher();
        LogUtils.INFO("Teacher", "Add New Teacher");
        USERINPUT("enter teacher's name: ");
        String name = mScanner.nextLine();
        teacher.setName(name);
        teacher.setRegDate(String.valueOf(System.currentTimeMillis()));
        teacher.setId(mBaseDao.getANewId());
        teacher.setStatus(0);
        teacher.setSkills(new ArrayList<>());
        if (register(teacher)) {
            LogUtils.SUCCESS("Teacher added successfully: " + teacher.getName());
        } else {
            WARNING("Teacher", "Failed to add teacher:");
        }
    }

    public void train() {
        INFO("Teacher", "Train Teacher");
        showTeacherDetailsShort();
        int id = -2;
        while (true) {
            try {
                USERINPUT("\nEnter Teacher ID: ");
                id = Integer.parseInt(mScanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        Optional<Teacher> toBeTrainedTeacher = mBaseDao.read(id);
        if (toBeTrainedTeacher.isPresent()) {
            Teacher teacher = toBeTrainedTeacher.get();
            List<Integer> selectedSkillIds = null;
            while (true) {
                INFO("Skills", "Available Skills:");
                skillController.showSkillList();
                USERINPUT("\nSelect skills by typing IDs separated by spaces: ");
                String input = mScanner.nextLine();
                try {
                    selectedSkillIds = java.util.Arrays.stream(input.split(" ")).map(Integer::parseInt).toList();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid skill IDs separated by spaces.");
                }
            }
            if (teacher.getSkills().size() > 0) {
                ArrayList newSkills = new ArrayList(selectedSkillIds);
                newSkills.addAll(teacher.getSkills());

                Set<Integer> set = new HashSet<>(newSkills);
                newSkills = new ArrayList<>(set);
                teacher.setSkills(newSkills);
            } else {
                teacher.setSkills(selectedSkillIds);
            }
            if (register(teacher)) {
                LogUtils.SUCCESS("Teacher added successfully: " + teacher.getId());
            } else {
                WARNING("Teacher", "Failed to add teacher:");
            }
        } else {
            WARNING("Teacher", "Teacher not exist");
        }

    }

    private boolean register(Teacher teacher) {
        return mBaseDao.update(teacher.getId(), teacher);
    }

    public void showTeacherSkills(Teacher teacher) {
        List<Teacher> teacherList = mBaseDao.readByField(EntityDao.FIELD_ID, String.valueOf(teacher.getId()));
        showTeacherDetailsShort(teacher);
        for (Teacher teacherItem : teacherList) {
            for (Integer skillId : teacherItem.getSkills()) {
                skillController.showSKillsById(skillId);
            }
        }
        System.out.print("\n");
    }

    public void showTeacherSkills(int teacherId) {
        Optional<Teacher> teacher = mBaseDao.read(teacherId);
        if (teacher.isPresent()) {
            showTeacherSkills(teacher.get());
        } else {
            WARNING(String.valueOf(teacherId), "Teacher not found");
        }
    }

    public void showALLTeacherSkills() {
        try {
            Iterator<Teacher> teachers = mBaseDao.queryAll().iterator();
            while (teachers.hasNext()) {
                showTeacherSkills(teachers.next());
            }
        } catch (Exception e) {
            WARNING("Teacher", "No Teachers found");
        }
    }

    public void queryTeacher() {
        INFO("Teacher", "Query Teacher Details");
        while (true) {
            USERINPUT("Search by (1) ID or (2) Name? Enter your choice: ");
            String choice = mScanner.nextLine().trim();

            if ("1".equals(choice)) {
                USERINPUT("Enter Teacher ID: ");
                try {
                    int id = Integer.parseInt(mScanner.nextLine().trim());
                    showTeacher(id);
                    System.out.println("Skills:");
                    showTeacherSkills(id);
                } catch (NumberFormatException e) {
                    WARNING("Teacher", "Invalid input. Please enter a valid teacher ID.");
                }
                break;
            } else if ("2".equals(choice)) {
                USERINPUT("Enter Teacher Name (case-insensitive): ");
                String name = mScanner.nextLine().trim();
                List<Teacher> teachers = mBaseDao.queryAll().stream().filter(t -> t.getName().equalsIgnoreCase(name)).toList();
                if (!teachers.isEmpty()) {
                    for (Teacher teacher : teachers) {
                        showTeacherDetailsShort(teacher);
                        showTeacherSkills(teacher);
                    }
                } else {
                    WARNING("Teacher", "No teacher found with the given name.");
                }
                break;
            } else {
                WARNING("Teacher", "Invalid choice. Please select a valid option.");
            }
        }
    }

}
