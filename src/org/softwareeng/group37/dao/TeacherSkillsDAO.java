package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.TeacherSkills;

public class TeacherSkillsDAO extends EntityDao<TeacherSkills> {

    private final static String TEACHERSKILLS_FILE =  "teacherSkills.csv";
    static TeacherSkillsDAO mInstance = null;

    public static TeacherSkillsDAO getInstance() {
        if (null == mInstance) {
            mInstance = new TeacherSkillsDAO();
        }
        return mInstance;
    }

    private TeacherSkillsDAO(String filename) {
        super(filename, TeacherSkills.class);
    }

    private TeacherSkillsDAO() {
        super(TEACHERSKILLS_FILE, TeacherSkills.class);
    }

}

