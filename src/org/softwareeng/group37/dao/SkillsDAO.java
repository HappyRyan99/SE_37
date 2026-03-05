package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.Skills;

public class SkillsDAO extends EntityDao<Skills> {
    private final static String SKILLS_FILE = "skills.csv";

    private SkillsDAO() {
        super(SKILLS_FILE, Skills.class);
    }

    private SkillsDAO(String fileName) {
        super(fileName, Skills.class);
    }

    private static SkillsDAO mSkillDao;

    public static SkillsDAO getInstance() {
        if (null == mSkillDao) {
            mSkillDao = new SkillsDAO(SKILLS_FILE);
        }
        return mSkillDao;
    }

}
