package org.softwareeng.group37.controller;

import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

import static org.softwareeng.group37.utils.LogUtils.INFO;
import static org.softwareeng.group37.utils.LogUtils.MENU;

public class MainController {
    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        LogUtils.changeOutputColor("GREEN"); // Set the output color to blue

        INFO("APP","Welcome to the Part time Teacher Management System");
        LogUtils.changeOutputColor("BLUE");
        while (true) {
            if (loginController.login()) {
                break;
            }
        }
        while (true) {
            MENU("HOME MENU");
            MENU(" 1. REGISTER NEW USER");
            MENU(" -2. LOGOUT");
            MENU("-1. EXIT");
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choice;
            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }

            switch (choice) {
                case 1:
                    loginController.register();
                    break;
                case -2:
                    INFO("APP","Logging out...");
                    while (true) {
                        if (loginController.login()) {
                            break;
                        }
                    }
                    break;
                case -1:
                    INFO("APP","Exiting...");
                    System.exit(0);
                    break;
            }

        }
    }
}
