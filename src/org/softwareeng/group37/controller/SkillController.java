package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.SkillsDAO;
import org.softwareeng.group37.model.Skills;
import org.softwareeng.group37.utils.LogUtils;

import java.util.List;
import java.util.Optional;

import static org.softwareeng.group37.utils.LogUtils.*;

public class SkillController extends BaseController {

    public SkillController() {
        super(Skills.class);
        mBaseDao = SkillsDAO.getInstance();
    }

    public static void main(String[] args) {
        SkillController skillController = new SkillController();
        skillController.addSkill();
        skillController.showSkillList();
    }

    public void addSkill() {
        Skills newSkill = new Skills();
        INFO("Skill", "Add New Skill");
        USERINPUT("Enter SkillName: ");
        String skillName = mScanner.nextLine();

        USERINPUT("Enter Description: ");
        String Description = mScanner.nextLine();

        newSkill.setId(mBaseDao.getANewId());
        newSkill.setSkillName(skillName);
        newSkill.setDescription(Description);
        mBaseDao.add(newSkill);
    }

    public void showFullSkillList() {
        List skills = mBaseDao.readAll();
        for (Object skill : skills) {
            showSkillDetails((Skills) skill);
        }
    }

    public void showSkillList() {
        List skills = mBaseDao.readAll();
        for (Object skill : skills) {
            showSkillShort((Skills) skill);
        }
    }

    public void showSKillsById(int skillId) {
        Optional skill = mBaseDao.read(skillId);
        if (skill.isPresent()) {
            showSkillDetails((Skills) skill.get());
        } else {
            USERINPUT("N/A ");
        }
    }

    public void showSkillDetails(Skills skill) {
        LogUtils.changeOutputColor("CYAN");
        System.out.print("ID: ");
        LogUtils.changeOutputColor("GREEN");
        System.out.println(skill.getId());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("Skill Name: ");
        LogUtils.changeOutputColor("YELLOW");
        System.out.println(skill.getSkillName());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("Description: ");
        LogUtils.changeOutputColor("BLUE");
        System.out.println(skill.getDescription());
        System.out.println("\t");
        LogUtils.resetOutputColor();
    }

    public void showSkillShort(Skills skill) {
        LogUtils.changeOutputColor("CYAN");
        System.out.print("ID: ");
        LogUtils.changeOutputColor("GREEN");
        System.out.println(skill.getId());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("Skill Name: ");
        LogUtils.changeOutputColor("YELLOW");
        System.out.println(skill.getSkillName());
        System.out.println("\t");
        LogUtils.resetOutputColor();
    }


}
