package org.softwareeng.group37.model;

import java.util.List;

public class TeacherSkills extends Entity{
    int teacherId;
    List<Integer> skills;

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    @Override
    public String toWrite() {
        if (getId() == -1) {
            return "id,status,teacherId,skillIds";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(getId()).append(",");
        sb.append(getStatus()).append(",");
        sb.append(teacherId).append(",");
        for (Integer skill : skills) {
            sb.append(skill).append("|");
        }
        if (sb.toString().endsWith("|")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TeacherSkills: ").append(getId()).append(" ")
                .append(getStatus()).append(" ")
                .append(teacherId).append(" ");
        for (Integer skill : skills) {
            sb.append(skill).append(",");
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

}
