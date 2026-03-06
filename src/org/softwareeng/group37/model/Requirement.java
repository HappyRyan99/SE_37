package org.softwareeng.group37.model;

import java.util.List;

public class Requirement extends Entity{

    private String requirementName;
    private int teacherId =0;
    private List<Integer>  skills;
    private String createDate;


    @Override
    public String toWrite() {
        if (getId() == -1) {
            return "id,status,requirementName,teacherId,skillIds";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(getId()).append(",");
        sb.append(getStatus()).append(",");
        sb.append(requirementName).append(",");
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
        if (getId() == -1) {
            return "id,status,requirementName,teacherId,skillIds";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(getId()).append(",");
        sb.append(getStatus()).append(",");
        sb.append(requirementName).append(",");
        sb.append(teacherId).append(",");
        for (Integer skill : skills) {
            sb.append(skill).append("|");
        }
        if (sb.toString().endsWith("|")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
