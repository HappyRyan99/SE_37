package org.softwareeng.group37.model;

import org.softwareeng.group37.utils.LogUtils;

import java.lang.reflect.Field;

public class Teacher extends Entity {


    private String name;
    private String regDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

//
//    @Override
//    public String toWrite() {
//        String te = toWriteId() + super.toWrite();
//        return te;
//    }
}
