package org.softwareeng.group37.controller;

import org.softwareeng.group37.dao.EntityDao;
import org.softwareeng.group37.model.Entity;
import org.softwareeng.group37.model.User;
import org.softwareeng.group37.utils.LogUtils;
import org.softwareeng.group37.utils.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.softwareeng.group37.utils.LogUtils.*;

public class LoginController {
    private final EntityDao<User> USER_DAO;
    private final String USER_FILE = "users.csv";
    public static User LOGIN_USER;

    public LoginController() {
        USER_DAO = new EntityDao<>(USER_FILE, User.class);
    }

    public boolean login() {
        if (!hasUsers()) {
            WARNING( "LOGIN","No users found. Please register first.");
            return register();
        }
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        INFO( "LOGIN","Welcome to the LOGIN Interface");
        USERINPUT("Enter username: ");
        String username = scanner.nextLine();

        USERINPUT("Enter password: ");
        String password = scanner.nextLine();

        return login(username, password);
    }

    private boolean hasUsers() {
        List<User> users = USER_DAO.readAll();
        DEBUG( "LOGIN","Number of users: " + users.size());
        for (User user : users) {
            DEBUG( "LOGIN","User: " + user.getUsername() + " ID: " + user.getId());
        }
        return users.size() > 0;
    }

    private boolean login(String username, String password) {
            List<User> users = USER_DAO.readByField("username", username);
            if (users.isEmpty()) {
                WARNING( "LOGIN","User not found");
                return false;
            }
            for (User user : users) {
                if (user.getPassword().equals(encryptPassword(password))) {
                    LOGIN_USER = user;
                    LogUtils.SUCCESS( "User logged in successfully: " + user.getUsername());
                    return true;
                }
            }
            WARNING( "LOGIN","Incorrect username or password");
            return false;
//        return USER_DAO.readAll().stream()
//                .filter(user -> user.getUsername().equals(username)
//                        && user.getPassword().equals(encryptPassword(password)))
//                .findFirst()
//                .map(authenticatedUser -> {
//                    LOGIN_USER = authenticatedUser;
//                    LogUtils.WARNING(getClass().getName(), "User logged in successfully: " + authenticatedUser.getUsername());
//                    return true;
//                })
//                .orElse(false);
    }


    public boolean register() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        INFO("LOGIN", "Welcome to the REGISTER Interface");
        USERINPUT("Enter username: ");
        String username = scanner.nextLine();
        USERINPUT("Enter password: ");
        String password = scanner.nextLine();
        if (username.isEmpty() || password.isEmpty()) {
            LogUtils.WARNING(getClass().getName(), "Username or password cannot be empty");
            return false;
        }
        User user = new User();
        user.setId(USER_DAO.getANewId());
        user.setUsername(username);
        user.setPassword(password);
        LOGIN_USER = user;
        boolean res = register(user);
        if (res){
            LogUtils.SUCCESS( "User registered successfully: " + user.getUsername());
            return true;
        }
        return false;
    }

    public boolean register(User user) {
        user.setPassword(encryptPassword(user.getPassword()));

        return USER_DAO.write(user);
    }
    
    public void listUsers() {
        List<User> users = USER_DAO.readAll();
        SUCCESS("Number of users: " + users.size());
        // Using ANSI color codes to enhance the output
        String colorUsername = "\u001B[34m"; // Blue for username
        String colorId = "\u001B[32m"; // Green for ID
        String colorReset = "\u001B[0m"; // Reset color after printing

        for (User user : users) {
            System.out.println(colorUsername + "Username: " + user.getUsername() +
                    colorReset + " , " + colorId + "ID: " + user.getId() + colorReset);
        }
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
