package org.softwareeng.group37.controller;

import org.softwareeng.group37.model.User;
import org.softwareeng.group37.utils.LogUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.softwareeng.group37.utils.LogUtils.*;

/**
 * This class handles the login, registration, and user management functionality.
 */
public class LoginController extends BaseController<User> {
    /**
     * The data access object for managing User entities.
     */
    private final static String USER_FILE = "users.csv";
    public static User LOGIN_USER;

    public LoginController() {
        super(USER_FILE, User.class);
    }

    /**
     * Handles the login process for a user by checking their credentials.
     * If no users exist, prompts the user to register first.
     *
     * @return true if login is successful, false otherwise.
     */
    public boolean login() {
        if (!hasUsers()) {
            WARNING("LOGIN", "No users found. Please register first.");
            return register();
        }
        INFO("LOGIN", "Welcome to the LOGIN Interface");
        USERINPUT("Enter username: ");
        String username = mScanner.nextLine();

        USERINPUT("Enter password: ");
        String password = mScanner.nextLine();

        return login(username, password);
    }

    /**
     * Validates if there are existing users in the system.
     *
     * @return true if users exist, false otherwise.
     */
    private boolean hasUsers() {
        List<User> users = mBaseDao.readAll();
        DEBUG("LOGIN", "Number of users: " + users.size());
        return !users.isEmpty();
    }

    private boolean login(String username, String password) {
        List<User> users = mBaseDao.readByField("username", username);
        if (users.isEmpty()) {
            WARNING("LOGIN", "User not found");
            return false;
        }
        for (User user : users) {
            if (user.getPassword().equals(encryptPassword(password))) {
                LOGIN_USER = user;
                LogUtils.SUCCESS("User logged in successfully: " + user.getUsername());
                return true;
            }
        }
        WARNING("LOGIN", "Incorrect username or password");
        return false;
    }


    /**
     * Handles the registration process for a new user by collecting
     * their username and password and saving them to the database.
     *
     * @return true if registration is successful, false otherwise.
     */
    public boolean register() {
        INFO("LOGIN", "Welcome to the REGISTER Interface");
        USERINPUT("Enter username: ");
        String username = mScanner.nextLine();
        USERINPUT("Enter password: ");
        String password = mScanner.nextLine();
        if (username.isEmpty() || password.isEmpty()) {
            LogUtils.WARNING(getClass().getName(), "Username or password cannot be empty");
            return false;
        }
        User user = new User();
        user.setId(mBaseDao.getANewId());
        user.setUsername(username);
        user.setPassword(password);
        LOGIN_USER = user;
        boolean res = register(user);
        if (res) {
            LogUtils.SUCCESS("User registered successfully: " + user.getUsername());
            return true;
        }
        return false;
    }

    public boolean register(User user) {
        user.setPassword(encryptPassword(user.getPassword()));
        mBaseDao.add(user);
        // TODO: 2026/3/3 what boolean means ?
        return true;
    }

    public void listUsers() {
        List<User> users = mBaseDao.readAll();
        SUCCESS("Number of users: " + users.size());
        // Using ANSI color codes to enhance the output
        String colorUsername = "\u001B[34m"; // Blue for username
        String colorId = "\u001B[32m"; // Green for ID

        String colorStatus = "\u001B[33m"; // Yellow for status
        String colorReset = "\u001B[0m"; // Reset color after printing

        for (User user : users) {
            System.out.println(colorUsername + "Username: " + user.getUsername() + colorReset + " , " + colorId + "ID: " + user.getId() + colorReset + " , " + colorStatus + "Status: " + user.getStatus() + colorReset);
        }
    }

    /**
     * Encrypts a plain-text password using SHA-256 hashing for secure storage.
     *
     * @param password the plain-text password to encrypt.
     * @return the hashed password as a hexadecimal string.
     */
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
