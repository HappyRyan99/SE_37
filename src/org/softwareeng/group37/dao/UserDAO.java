package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.User;
import org.softwareeng.group37.model.User;

public class UserDAO extends EntityDao<User> {
    private final static String USER_FILE = "users.csv";

    private UserDAO() {
        super(USER_FILE, User.class);
    }

    private UserDAO(String fileName) {
        super(fileName, User.class);
    }

    private static UserDAO mUserDao;

    public static UserDAO getInstance() {
        if (null == mUserDao) {
            mUserDao = new UserDAO(USER_FILE);
        }
        return mUserDao;
    }

}
