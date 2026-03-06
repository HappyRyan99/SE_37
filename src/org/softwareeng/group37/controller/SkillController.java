package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.SkillsDAO;
import org.softwareeng.group37.model.Skills;
import org.softwareeng.group37.utils.LogUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.softwareeng.group37.utils.LogUtils.*;

public class SkillController extends BaseController<Skills> {

    public SkillController() {
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
        List<Skills> skills = mBaseDao.queryAll();
        for (Skills skill : skills) {
            showSkillDetails(skill);
        }
    }

    public void showSkillList() {
        Iterator<Skills> skills = mBaseDao.queryAll().iterator();
        while (skills.hasNext()) {
            showSkillShort(skills.next());
        }
    }

    public void showSKillsById(int skillId) {
        Optional<Skills> skill = mBaseDao.read(skillId);
        if (skill.isPresent()) {
            showSkillDetails(skill.get());
        } else {
            USERINPUT("N/A ");
        }
    }

    public void showSkillDetails(Skills skill) {
        LogUtils.changeOutputColor("CYAN");
        System.out.print("\n\t Skill ID: ");
        LogUtils.changeOutputColor("GREEN");
        System.out.print(skill.getId());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("\tSkill Name: ");
        LogUtils.changeOutputColor("YELLOW");
        System.out.print(skill.getSkillName());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("\tDescription: ");
        LogUtils.changeOutputColor("BLUE");
        System.out.print(skill.getDescription());
        LogUtils.resetOutputColor();
    }

    public void showSkillShort(Skills skill) {
        LogUtils.changeOutputColor("CYAN");
        System.out.print("ID: ");
        LogUtils.changeOutputColor("GREEN");
        System.out.print(skill.getId());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("\tSkill Name: ");
        LogUtils.changeOutputColor("YELLOW");
        System.out.print(skill.getSkillName());
        LogUtils.resetOutputColor();
    }


}
