package org.softwareeng.group37.controller;

import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.softwareeng.group37.utils.LogUtils.*;

/**
 * MainController class serves as the entry point of the application.
 * It manages the user authentication and navigates between the main menu options.
 */
public class MainController {

    /**
     * The main method initializes the LoginController, handles user login,
     * and provides access to the home menu functionality.
     */

    static List<BaseController> controllers;


    public static void main(String[] args) {
        controllers = new ArrayList<BaseController>();

        SkillController skillController = new SkillController();
        controllers.add(skillController);
        // Instantiate the LoginController to handle user authentication
        LoginController loginController = new LoginController();
        TeacherController teacherController = new TeacherController();

        // Change the output color to green for the welcome message
        LogUtils.changeOutputColor("GREEN");

        // Display the welcome message to the user
        INFO("APP", "Welcome to the Part time Teacher Management System");
        LogUtils.changeOutputColor("BLUE");

        // Loop to continuously prompt user for valid login credentials
        while (true) {
            if (loginController.login()) {
                break;
            }
        }

        // Loop to display the home menu and handle user menu interactions
        while (true) {
            MENU("\n=========================== HOME MENU ===================================\n");
            MENU("==== USER MENU===\n");
            MENU(" 1. REGISTER NEW USER\t");
            MENU(" 2. LIST USERS\t");
            MENU("\n");
            MENU("==== TEACHER MENU===\n ");
            MENU(" 3. REGISTER NEW TEACHER\t");
            MENU(" 4. LIST TEACHERS\t");
            MENU("\n");
            MENU("==================== QUIT =================================================\n");
            MENU("-1. LOGOUT\t");
            MENU("-2. EXIT\t");
            MENU("\n");

            // Prompt user for input and ensure valid integer choice is entered
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choice;
            while (true) {
                try {
                    USERINPUT("Enter your choice: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }

            // Handle user menu option based on their choice
            switch (choice) {
                case 1: // Case for registering a new user
                    loginController.register();
                    break;
                case 2: // Case for listing all registered users
                    loginController.listUsers();
                    break;
                case 3:
                    teacherController.addTeacher();
                    break;
                case 4:
                    teacherController.showTeacherList();
                    break;
                case -1: // Case for logging out and returning to the login prompt
                    INFO("APP", "Logging out...");
                    while (true) {
                        if (loginController.login()) {
                            break;
                        }
                    }
                    break;
                case -2: // Case for exiting the application
                    if (null != controllers) {
                        for (BaseController baseController : controllers) {
                            baseController.finish();
                        }
                    }
                    INFO("APP", "Exiting...");
                    System.exit(0);
                    break;
            }
        }
    }
}
