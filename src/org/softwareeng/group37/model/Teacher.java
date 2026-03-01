package org.softwareeng.group37.model;

public class Teacher extends Entity{


    private String name;
    private long regDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toWrite() {
        return "";
    }

}
