package org.softwareeng.group37.model;

import java.util.List;

public class Teacher extends Entity {

    private String name;
    List<Integer> skills;
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

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    public String skillsToString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getId()).append(",");
        sb.append(getStatus()).append(",");
        sb.append(getId()).append(",");
        for (Integer skill : skills) {
            sb.append(skill).append("|");
        }
        if (sb.toString().endsWith("|")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
