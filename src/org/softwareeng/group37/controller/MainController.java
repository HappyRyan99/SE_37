package org.softwareeng.group37.controller;

import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

public class MainController {
    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        LogUtils.changeOutputColor("BLUE"); // Set the output color to blue

        System.out.println("Welcome to the Part time Teacher Management System");
        LogUtils.resetOutputColor(); // Reset the color after printing
    }
}
