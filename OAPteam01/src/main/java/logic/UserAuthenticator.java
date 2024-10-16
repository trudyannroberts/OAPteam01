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
 * The UserAuthenticator class provides functionality for registering and logging in users.
 * It handles user validation, interacts with the database for storing user information, and manages 
 * profile loading from a file.
 * 
 * This class is integrated with a GUI using  JOptionPane for 
 * error messages and user feedback.
 * 
 * TODO: Password must be hashed
 * 
 * @author Trudy Ann Roberts
 */
public class UserAuthenticator {

    /**
     * Registers a new user in the system by inserting their details into the database.
     * 
     * Before the user is registered, the email format and password strength are validated.
     * If either validation fails, the registration process is aborted, and an error message 
     * is displayed.
     * 
     * @param user the User object containing user details to be registered
     * @return true if the registration was successful, or false otherwise
     */
    public static boolean registerUser(User user) {
        // Validate email
        if (!isValidEmail(user.getEmail())) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return false; // Stop further processing if email is invalid
        }

        // Validate password
        if (!isValidPassword(user.getPassword())) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, include at least one uppercase letter and one number.");
            return false; // Stop further processing if password is invalid
        }

        String query = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) " +
                       "VALUES (?, ?, 1, ?, 1, 1, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, user.getPassword()); // TODO: hashing the password here

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs in a user by validating their username and password against the records in the database.
     * 
     * If the login is successful, the user's profiles are loaded from a local file. If the user 
     * has profiles, they are displayed in a dialog for the user to select one.
     * 
     * @param username the username provided for login
     * @param password the password provided for login
     * @return the User object if login is successful, or  null if login fails
     */
    public static User logInUser(String username, String password) {
        // Ensure username and password are provided
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password must be provided.");
            return null;
        }

        String query = "SELECT * FROM staff WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String userId = resultSet.getString("staff_id");

                // Create User object after successful login
                user = new User(firstName, lastName, email, username, password);
                JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + user.getFirstName() + "!");

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
        return user;
    }

    /**
     * Loads the profiles associated with the given user ID from a local file and returns them as a list.
     * 
     * The method reads from a file, assuming the file contains comma-separated values where each 
     * line represents a profile. The third field in each line is expected to be the user ID.
     * 
     * @param userId the ID of the user whose profiles are to be loaded
     * @return a List of profile names associated with the user
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
     * Validates the given password to ensure it meets the required criteria.
     * 
     * The password must be at least 8 characters long, contain at least one uppercase letter,
     * and include at least one numeric digit.
     * 
     * @param password the password to validate
     * @return true if the password is valid, or false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false; // Ensure password is not empty
        }
        return password.length() >= 8 &&
               password.chars().anyMatch(Character::isUpperCase) &&
               password.chars().anyMatch(Character::isDigit);
    }

    /**
     * Validates the format of the given email address.
     * 
     * The email is considered valid if it matches a regular expression that allows common 
     * email formats.
     * 
     * @param email the email address to validate
     * @return true if the email is valid, or false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Ensure email is not empty
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
}
