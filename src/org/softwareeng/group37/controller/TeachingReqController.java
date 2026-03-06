package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.dao.RequirementsDAO;
import org.softwareeng.group37.dao.TeacherDAO;
import org.softwareeng.group37.model.Requirement;
import org.softwareeng.group37.model.Teacher;
import org.softwareeng.group37.utils.LogUtils;

import java.util.*;


public class TeachingReqController extends BaseController {

    TeacherDAO mTeacherDao = null;
    TeacherController mTeacherController;
    SkillController mSkillController;

    public TeachingReqController() {
        mBaseDao = RequirementsDAO.getInstance();
        mTeacherDao = TeacherDAO.getInstance();
        mTeacherController = new TeacherController();
        mSkillController = new SkillController();
    }

    public void showRequirementList() {
        List<Requirement> requirements = mBaseDao.queryAll();
        for (Requirement requirement : requirements) {
            showRequirement(requirement);
            LogUtils.changeOutputColor("BLUE");
            System.out.print("---------------------------------------------------------------------------------------\n");
            LogUtils.resetOutputColor();
        }
    }
    public void showRequirement(Requirement requirement) {
        LogUtils.changeOutputColor("CYAN");
        System.out.print("ID: ");
        LogUtils.changeOutputColor("GREEN");
        System.out.print(requirement.getId());

        LogUtils.changeOutputColor("CYAN");
        System.out.print("\tName: ");
        LogUtils.changeOutputColor("YELLOW");
        System.out.print(requirement.getRequirementName());

        if (requirement.getTeacherId() > 0){
            LogUtils.changeOutputColor("PURPLE");
            System.out.print("\tName: ");
            mTeacherController.showTeacherSHort(requirement.getTeacherId());
        }

        LogUtils.changeOutputColor("CYAN");
        System.out.print("\tStatus: ");
        if (requirement.getTeacherId()> 0){
            LogUtils.changeOutputColor("GREEN");
            System.out.print("Assigned");
        }
        else{
            LogUtils.changeOutputColor("YELLOW");
            System.out.print("Not Assigned");
        }

        for (Integer skillId : requirement.getSkills()){
            mSkillController.showSKillsById(skillId);
        }
        System.out.println("\t");

        LogUtils.resetOutputColor();
    }
    public void createRequirement() {
        LogUtils.INFO("Requirement", "Create New Teaching Requirement");
        LogUtils.USERINPUT("Enter Requirement Name: ");
        String requirementName = mScanner.nextLine();
        mSkillController.showSkillList();
        LogUtils.USERINPUT("\nEnter Skill IDs (space separated, e.g., 1 2 3): ");
        List<Integer> skillIds;
        while (true) {
            try {
                String[] skillIdsInput = mScanner.nextLine().split(" ");
                skillIds = new ArrayList<>();
                for (String skillId : skillIdsInput) {
                    skillIds.add(Integer.parseInt(skillId.trim()));
                }
                break;
            } catch (NumberFormatException e) {
                LogUtils.WARNING("Requirement", "Invalid input. Please enter valid skill IDs.");
            }
        }

        Requirement requirement = new Requirement();
        requirement.setId(mBaseDao.getANewId());
        requirement.setRequirementName(requirementName);
        requirement.setSkills(skillIds);
        mBaseDao.add(requirement);

        LogUtils.USERINPUT("Do you want to assign a teacher to this requirement? (y/n): ");
        String assignChoice = mScanner.nextLine();
        if (assignChoice.equalsIgnoreCase("y")) {
            assignTeacherToRequirement(requirement);
        }
    }
    public void assignTeacherToRequirement() {
        LogUtils.INFO("Requirement", "Assign Teacher to Requirement");
        List<Requirement> requirements = mBaseDao.queryAll();
        for (Requirement requirement : requirements) {
            if (requirement.getTeacherId() < 1){
                showRequirement(requirement);
                System.out.print("\n");
            }
        }
        LogUtils.USERINPUT("Enter Requirement ID: ");
        Optional<Requirement> requirementOP = null;
        while (true){
            int requirementId;
            try {
                requirementId = Integer.parseInt(mScanner.nextLine());
                requirementOP = mBaseDao.read(requirementId);
                break;
            } catch (NumberFormatException e) {
                LogUtils.WARNING("Requirement", "Invalid ID format. Please enter a numeric ID.");
            }
        }
        if (requirementOP.isPresent()) {
            Requirement requirement = requirementOP.get();
            assignTeacherToRequirement(requirement);
        } else {
            LogUtils.WARNING("Requirement", "Requirement not found.");
        }
    }

    private void assignTeacherToRequirement(Requirement requirement) {
        LogUtils.INFO("Requirement", "Assign Teacher to Requirement: "+requirement.getRequirementName());
        mTeacherController.showTeacherDetailsShort();
        LogUtils.USERINPUT("\nPlease enter the ID of the teacher you want to assign:");
        Teacher teacher =null;
        while (true) {
            try {
                int teacherId = Integer.parseInt(mScanner.nextLine());
                Optional<Teacher> teacherOP = mTeacherDao.read(teacherId);

                if (teacherOP.isPresent()) {
                    teacher = teacherOP.get();
                    break;
                } else {
                    LogUtils.WARNING("","Invalid Teacher ID. Please try again.");
                }
            } catch (NumberFormatException e) {
                LogUtils.WARNING("","Invalid input. Please enter a numeric Teacher ID.");
            }
        }
        List<Teacher> availableTeacherSkills = mTeacherDao.readByField(EntityDao.FIELD_ID, String.valueOf(teacher.getId()));
        Set<Integer> requiredSkills = new HashSet<>(requirement.getSkills());
        if (!availableTeacherSkills.isEmpty()) {
                availableTeacherSkills.getFirst().getSkills().forEach(requiredSkills::remove);
        }
        if (!requiredSkills.isEmpty()) {
            LogUtils.WARNING("Requirement", "The selected teacher is missing the following required skills: " + requiredSkills);
            LogUtils.USERINPUT("Do you want to train the teacher to gain these skills? (y/n): ");
            String trainChoice = mScanner.nextLine();
            if (trainChoice.equalsIgnoreCase("y")) {
                LogUtils.INFO("Requirement", "Training the teacher on missing skills...");
                // Logic to train the teacher can be added here
                availableTeacherSkills.getFirst().getSkills().addAll(requiredSkills);
                mTeacherDao.update(teacher.getId(), availableTeacherSkills.getFirst());
            } else {
                LogUtils.WARNING("Requirement", "Teacher was not trained. Assignment process aborted.");
                return;
            }
        }
        
        requirement.setTeacherId(teacher.getId());
        if ( mBaseDao.update(requirement.getId(), requirement)){
            LogUtils.WARNING("Requirement", "Teacher assigned successfully.");
        }else{
            LogUtils.WARNING("Requirement", "Teacher assignment failed.");
        }
    }
}
