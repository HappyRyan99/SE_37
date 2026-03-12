package org.softwareeng.group37;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.Skills;

import java.util.List;
import java.util.Optional;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test.test1();
    }


    void test1() {
        EntityDao baseDao = new EntityDao<Skills>("skills.csv", Skills.class);
        List<Skills> all = baseDao.queryAll();
        for (Skills skill : all) {
            System.out.print(skill.toString());
        }

//        for (int i = 0; i < 10; i++) {
//            Skills skills = new Skills();
//            skills.setId(baseDao.getANewId());
//            skills.setSkillName("SkillName:" + i);
//            skills.setDescription("SkillDescription:" + i);
//            baseDao.add(skills);
//        }

        System.out.println("===================queryAll========================");

        List<Skills> newSk = baseDao.queryAll();
        for (Skills skill : newSk) {
            System.out.print(skill.toString());
        }
        Optional<Skills> optional = baseDao.read(29);
        if (optional.isPresent()) {
            Skills data = optional.get();
            data.setDescription("New Description");
            baseDao.update(data.getId(),data);
        } else {
        }
        System.out.println("===================Write====================");
        baseDao.flush();
    }
}
