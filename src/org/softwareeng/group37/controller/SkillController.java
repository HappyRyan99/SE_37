package org.softwareeng.group37.controller;

import org.softwareeng.group37.model.Skills;
import org.softwareeng.group37.utils.Utils;

import java.util.List;

import static org.softwareeng.group37.utils.LogUtils.INFO;
import static org.softwareeng.group37.utils.LogUtils.USERINPUT;

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

    public void showSkillList() {
        List skills = baseDao.readAll();
        Utils.printScreen(skills.toString());
    }



}
