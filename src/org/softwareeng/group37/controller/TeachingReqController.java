package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.RequirementsDAO;
import org.softwareeng.group37.dao.TeacherDAO;
import org.softwareeng.group37.dao.TeacherSkillsDAO;
import org.softwareeng.group37.model.Requirement;
import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.model.TeacherSkills;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class TeachingReqController extends BaseController {

    TeacherDAO mTeacherDao = null;
    TeacherSkillsDAO mTeacherSkillsDAO = null;
    ;

    public TeachingReqController() {
        mBaseDao = RequirementsDAO.getInstance();
        mTeacherDao = TeacherDAO.getInstance();
        mTeacherSkillsDAO = TeacherSkillsDAO.getInstance();
    }


    private void createATeachingRequest(String name, int skillId, List<Teacher> teachers) {
        Requirement requirement = new Requirement();
        requirement.setId(mBaseDao.getANewId());
        requirement.setStatus(0);
        requirement.setSkillId(skillId);
        requirement.setTeachers(teachers);
        requirement.setRequirementName(name);
        mBaseDao.add(requirement);
    }

    private void showAllReqList() {
        Iterator<Requirement> allReq = mBaseDao.queryAll().iterator();
        while (allReq.hasNext()) {
            Requirement req = allReq.next();
            System.out.println(req);
        }
    }

    private void showReqirementDetail(int id) {
        Optional<Requirement> optional = mBaseDao.read(id);
        optional.ifPresent(req -> {
            System.out.println(req);
        });
    }

    private int showSkillAvailableTeacher(int skillId) {
        List<TeacherSkills> availableTeacher = mTeacherSkillsDAO.readByField("skills", String.valueOf(skillId));
        if (availableTeacher.isEmpty()) {
            // TODO: 2026/3/4 get into recruit process
            return -1;
        } else {
            // TODO: 2026/3/4 choose one teacher
            for (TeacherSkills teacherSkills : availableTeacher) {
                System.out.println("teacherId: " + teacherSkills.getTeacherId());
            }
            return 1;
        }
    }


}
