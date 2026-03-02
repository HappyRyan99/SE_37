package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.TeacherSkills;

public class TeacherSkillsDAO extends EntityDao<TeacherSkills>{
    public TeacherSkillsDAO(String filename) {
        super(filename, TeacherSkills.class);
    }

    public TeacherSkillsDAO() {
        super("teacherSkills.csv",TeacherSkills.class);
    }

    @Override
    public boolean write(TeacherSkills data) {
        return super.write(data);
    }
}
