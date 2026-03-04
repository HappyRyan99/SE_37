package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.Requirement;
import org.softwareeng.group37.model.Teacher;

public class RequirementsDAO extends EntityDao<Requirement> {
    public final static String REQUIREMENT_FILE = "requirement.csv";

    private RequirementsDAO() {
        super(REQUIREMENT_FILE, Teacher.class);
    }

    private RequirementsDAO(String fileName) {
        super(fileName, Teacher.class);
    }

    private static RequirementsDAO mRequirementDao;

    public static RequirementsDAO getInstance() {
        if (null == mRequirementDao) {
            mRequirementDao = new RequirementsDAO(REQUIREMENT_FILE);
        }
        return mRequirementDao;
    }

}
