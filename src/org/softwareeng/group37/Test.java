package org.softwareeng.group37;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.Entity;
import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.model.User;
import org.softwareeng.group37.utils.InitDatabase;
import org.softwareeng.group37.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test.test1();
    }


    void test1() {
        InitDatabase.init();

        Teacher teacher = new Teacher();
        teacher.setName("test");
        teacher.setRegDate("1234567890");
        teacher.setId(12);
        teacher.setStatus(0);

        User user = new User();
        user.setId(122);
        user.setUsername("tasdfasdfaest");
        user.setPassword("dafsdfa");
        user.setStatus(1);

//        System.out.println(teacher.getHeader());
//        System.out.println(teacher.toWrite());
//        System.out.println(user.getHeader());
//        System.out.println(user.toWrite());

        EntityDao entityDao = new EntityDao("users.csv", User.class);
        List<User> userList = entityDao.readAll();
        System.out.println("==========userlist size" + userList.size());
//        entityDao.write(user);
        for (User u : userList) {
            System.out.println(u.toWrite());
            System.out.println("=======getUsername==========" + u.getUsername());
            System.out.println("=======getStatus==========" + u.getStatus());
        }
        Optional<User> u122 = entityDao.read(122);
        if (u122.isPresent()) {
            System.out.println("=======u122 isPresent==========");
            System.out.println(u122.get().toString());
        } else {
            System.out.println("=======u122==========" + "not found");
        }
    }
}
