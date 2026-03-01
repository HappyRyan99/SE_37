package org.softwareeng.group37;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String CSV_FILE = "users.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Application");
        
        if (login(scanner)) {
            System.out.println("Login successful!");
            // TODO: Add your application logic here
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
        
        scanner.close();
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return validateCredentials(username, password);
    }

    private static boolean validateCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    String storedUser = values[0].trim();
                    String storedPass = values[1].trim();
                    if (storedUser.equals(username) && storedPass.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("User database not found or error reading file. Please ensure " + CSV_FILE + " exists.");
        }
        return false;
    }
}
