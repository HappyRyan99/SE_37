package org.softwareeng.group37.controller;

import org.softwareeng.group37.model.Skills;
import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

import java.util.List;
import java.util.Optional;

import static org.softwareeng.group37.utils.LogUtils.*;

public class SkillController extends BaseController {

    private final static String SKILLS_FILE = "skills.csv";

    public SkillController() {
        super(SKILLS_FILE, Skills.class);
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
        String skillName = scanner.nextLine();

        USERINPUT("Enter Description: ");
        String Description = scanner.nextLine();

        newSkill.setId(baseDao.getANewId());
        newSkill.setSkillName(skillName);
        newSkill.setDescription(Description);
        baseDao.write(newSkill);
    }

    public void showFullSkillList() {
        List skills = baseDao.readAll();
        for (Object skill : skills) {
            showSkillDetails((Skills) skill);
        }
    }
    public void showSkillList() {
        List skills = baseDao.readAll();
        for (Object skill : skills) {
            showSkillShort((Skills) skill);
        }
    }
    public void showSKillsById(int skillId){
        Optional skill = baseDao.read(skillId);
        if(skill.isPresent()){
            showSkillDetails((Skills) skill.get());
        }else {
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
