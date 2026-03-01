package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.User;
import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class LoginController {
    private final EntityDao<User> USER_DAO;
    private final String USER_FILE = "users.csv";
    public static  User LOGIN_USER;

    public LoginController() {
        USER_DAO = new EntityDao<>(USER_FILE, User.class);
    }

    public boolean login(){
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Utils.printScreen( "Welcome to the LOGIN Interface");
        Utils.printScreen("Enter username: ");
        String username = scanner.nextLine();

        Utils.printScreen("Enter password: ");
        String password = scanner.nextLine();

        return login(username, password);
    }
    
    private boolean login(String username, String password) {
        return USER_DAO.readAll().stream()
                .filter(user -> user.getUsername().equals(username)
                        && user.getPassword().equals(encryptPassword(password)))
                .findFirst()
                .map(authenticatedUser -> {
                    LOGIN_USER = authenticatedUser;
                    LogUtils.DEBUG(getClass().getName(), "User logged in successfully: " + authenticatedUser.getUsername());
                    return true;
                })
                .orElse(false);
    }

    public boolean register() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println( "Welcome to the REGISTER Interface");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.println( "Enter password: ");
        String password = scanner.nextLine();
        if (username.isEmpty() || password.isEmpty()) {
            LogUtils.DEBUG(getClass().getName(), "Username or password cannot be empty");
            return false;
        }
        User user = new User();
        user.setId(Utils.generateNewUserId());
        user.setUsername(username);
        user.setPassword(password);
        return register(user);
    }
    public boolean register(User user) {
        user.setPassword(encryptPassword(user.getPassword()));

        return USER_DAO.write(user);
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }


}
