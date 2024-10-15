package logic;

import db.DatabaseConnection;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAuthenticator class is responsible for user registration and authentication
 * in the media streaming service application. It provides methods for validating
 * user input, registering new users, and logging in existing users. 
 * This class interacts with the database to store and retrieve user data.
 * 
 * @author Trudy Ann Roberts
 */
public class UserAuthenticator {

    /**
     * Registers a new user with the provided details.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user, which must be valid.
     * @param username  The username chosen by the user, which must be unique.
     * @param password  The password chosen by the user, which must meet complexity requirements.
     * @return true if the registration was successful; false otherwise.
     */
    public static boolean registerUser(String firstName, String lastName, String email, String username, String password) {
        // Validate email
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return false; // Stop further processing if email is invalid
        }

        // Validate password
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, include at least one uppercase letter and one number.");
            return false; // Stop further processing if password is invalid
        }

        String query = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) " +
                       "VALUES (?, ?, 1, ?, 1, 1, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password); // TODO: Hashing the password here

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return true if the login was successful; false otherwise.
     */
    public static boolean logInUser(String username, String password) {
        // Ensure username and password are provided
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password must be provided.");
            return false;
        }

        String query = "SELECT * FROM staff WHERE username = ? AND password = ?";
        boolean isLoggedIn = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String userId = resultSet.getString("staff_id");

                JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + firstName + "!");
                isLoggedIn = true;

                // Load and display profiles using GUI
                List<String> profiles = loadUserProfiles(userId);
                if (!profiles.isEmpty()) {
                    String selectedProfile = (String) JOptionPane.showInputDialog(
                            null,
                            "Select a profile:",
                            "Profile Selection",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            profiles.toArray(),
                            profiles.get(0)
                    );
                    if (selectedProfile != null) {
                        JOptionPane.showMessageDialog(null, "You selected profile: " + selectedProfile);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No profiles found for this user.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return isLoggedIn;
    }

    /**
     * Loads user profiles from a file based on the user ID.
     *
     * @param userId The ID of the user whose profiles are to be loaded.
     * @return A list of profile names associated with the specified user ID.
     */
    public static List<String> loadUserProfiles(String userId) {
        List<String> profiles = new ArrayList<>();
        String filePath = "profiles.txt"; // Assuming profiles are stored in this file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].trim().equals(userId)) {
                    profiles.add(parts[0].trim()); // Add the profile name to the list
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading profiles: " + e.getMessage());
        }
        
        return profiles;
    }

    /**
     * Validates the provided password against predefined criteria.
     *
     * @param password The password to validate.
     * @return true if the password meets the requirements; false otherwise.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("Password is empty or null.");
            return false; // Ensure password is not empty
        }
        boolean valid = password.length() >= 8 &&
                        password.chars().anyMatch(Character::isUpperCase) &&
                        password.chars().anyMatch(Character::isDigit);
        System.out.println("Password validation for '" + password + "': " + valid);
        return valid;
    }

    /**
     * Validates the provided email against standard email format.
     *
     * @param email The email address to validate.
     * @return true if the email is valid; false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            System.out.println("Email is empty or null.");
            return false; // Ensure email is not empty
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        boolean valid = email.matches(emailRegex);
        System.out.println("Email validation for '" + email + "': " + valid);
        return valid;
    }
}
