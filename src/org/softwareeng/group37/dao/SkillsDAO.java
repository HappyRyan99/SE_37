package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.Teacher;

public class SkillsDAO extends EntityDao<Teacher> {
    private final static String SKILLS_FILE = "skills.csv";

    private SkillsDAO() {
        super(SKILLS_FILE, Teacher.class);
    }

    private SkillsDAO(String fileName) {
        super(fileName, Teacher.class);
    }

    private static SkillsDAO mSkillDao;

    public static SkillsDAO getInstance() {
        if (null == mSkillDao) {
            mSkillDao = new SkillsDAO(SKILLS_FILE);
        }
        return mSkillDao;
    }

}
