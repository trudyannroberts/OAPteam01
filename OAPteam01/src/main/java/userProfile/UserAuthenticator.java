package userProfile;

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
 * The {@code UserAuthenticator} class is responsible for managing user authentication, 
 * registration, and profile loading operations. It acts as a controller by handling interactions 
 * between the GUI and the database.
 * 
 * The class provides methods to register users, log them in, validate email and password formats, 
 * and load user profiles from a local file.
 * 
 * @author Trudy Ann Roberts
 */
public class UserAuthenticator {

    /**
     * Registers a new user in the system by inserting their information into the database.
     * It validates the user's email and password before proceeding.
     *
     * @param user The User object containing the user's registration details.
     * @return true if the user was successfully registered or false otherwise.
     *         Displays an error message via the GUI if validation or registration fails.
     */
	public static boolean registerUser(User user) {
	    // Validate email
		boolean isValidEmail = false;
		boolean isValidPassword = false;
	    /**
	     * Calls the isValidEmail method to validate email.
	     * @boolean isValidEmail is used in the while loop that will continue to prompt the user to enter an 
	     * email as long as the email is invalid.
	     * @return false is the email is not valid.
	     */
	    while (!isValidEmail) {
	        if (!isValidEmail(user.getEmail())) {
	            JOptionPane.showMessageDialog(null, "Invalid email format.");
	            String newEmail = JOptionPane.showInputDialog("Please enter a valid email:");
	            if (newEmail == null) {
	                JOptionPane.showMessageDialog(null, "Registration cancelled.");
	                return false; // Exit if the user cancels
	            }
	            user.setEmail(newEmail); // Update the user email and re-validate
	        } else {
	            isValidEmail = true;
	        }
	    }
	    /**
	     * Calls the isValidPassword method to validate password.
	     * @boolean isValidPassword is used in the while loop that will continue to prompt the user to enter  
	     * a password as long as the password is invalid.
	     * @return false if the password is not valid.
	     */
	    while (!isValidPassword) {
	        if (!isValidPassword(user.getPassword())) {
	            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, and one number.");
	            String newPassword = JOptionPane.showInputDialog("Please enter a valid password:");
	            if (newPassword == null) {
	                JOptionPane.showMessageDialog(null, "Registration cancelled.");
	                return false; // Exit if the user cancels
	            }
	            user.setPassword(newPassword); // Update the user password and re-validate
	        } else {
	            isValidPassword = true;
	        }
	    }

	    // Proceed with registration
	    String query = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) " +
	                   "VALUES (?, ?, 1, ?, 1, 1, ?, ?, NOW())";

	    // Hash the password
	    String rawPassword = user.getPassword();
	    System.out.println("Raw Password during Registration: " + rawPassword);
	    String hashedPassword = PasswordHasher.hashPassword(rawPassword);
	    System.out.println("Hashed Password during Registration: " + hashedPassword);
	    System.out.println("Raw Password during Registration: " + user.getPassword()); // Print raw password
	    System.out.println("Hashed Password during Registration: " + hashedPassword);
	    
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setString(1, user.getFirstName());
	        pstmt.setString(2, user.getLastName());
	        pstmt.setString(3, user.getEmail());
	        pstmt.setString(4, user.getUsername());
	        pstmt.setString(5, hashedPassword); // Store hashed password

	        int rowsAffected = pstmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}



    /**
     * Logs in a user by checking their username and password against the database.
     * If the login is successful, the user's profiles are loaded from a local file.
     *
     * @param username The username provided by the user for login.
     * @param password The password provided by the user for login.
     * @return A  User object if the login is successful or null otherwise.
     *         Displays appropriate messages via the GUI for successful or failed login attempts.
     */
	public static User logInUser(String username, String password) {
	    if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Username and password must be provided.");
	        return null;
	    }

	    String query = "SELECT * FROM staff WHERE username = ?";
	    User user = null;

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setString(1, username);
	        ResultSet resultSet = pstmt.executeQuery();

	        if (resultSet.next()) {
	            // Retrieve stored hashed password from the database
	            String storedHashedPassword = resultSet.getString("password");

	            // Hash the entered password and compare
	            String hashedInputPassword = PasswordHasher.hashPassword(password);
	            System.out.println("Input password: " + password);
	            System.out.println("Hashed Input Password during Login: " + hashedInputPassword);
	            System.out.println("Stored Hashed Password: " + storedHashedPassword);

	            if (hashedInputPassword.equals(storedHashedPassword)) {
	                String firstName = resultSet.getString("first_name");
	                String lastName = resultSet.getString("last_name");
	                String email = resultSet.getString("email");
	                String userId = resultSet.getString("staff_id");

	                user = new User(firstName, lastName, email, username, storedHashedPassword);
	                JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + user.getFirstName() + "!");

	                // Load and display user profiles
	                List<String> profiles = loadUserProfiles(userId);
	                if (!profiles.isEmpty()) {
	                    String selectedProfile = (String) JOptionPane.showInputDialog(
	                            null, "Select a profile:", "Profile Selection",
	                            JOptionPane.QUESTION_MESSAGE, null, profiles.toArray(), profiles.get(0)
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
     * Loads the profiles associated with a given user from a local file.
     * Each profile is identified by the user's ID and stored in a text file.
     *
     * @param userId The unique ID of the user.
     * @return A list of profile names associated with the user.
     *         Displays an error message via the GUI if file reading fails.
     */
    public static List<String> loadUserProfiles(String userId) {
        List<String> profiles = new ArrayList<>();
        String filePath = "profiles.dat"; // Path to the file storing user profiles

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
     * Validates the format of a given password.
     * The password must be at least 8 characters long and contain at least one uppercase letter and one digit.
     *
     * @param password The password to validate.
     * @return true if the password meets the criteria or false otherwise.
     */
    public static boolean isValidPassword(String password) {
        // Check if the password is null or empty
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        // Initialize counts for uppercase letters, lowercase letters, and digits
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;

        // Iterate over the characters of the password
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(ch)) {
                lowercaseCount++;
            } else if (Character.isDigit(ch)) {
                digitCount++;
            }
        }

        // Check if password meets the criteria
        return uppercaseCount >= 1 && lowercaseCount >= 1 && digitCount >= 1 && password.length() >= 8;
    }
    /**
     * Validates the format of a given email address using a regular expression.
     *
     * @param email The email to validate.
     * @return  true if the email is valid or false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        /* The emailValidation checks whether the given email string matches the pattern defined by emailValidation. 
        It returns true if the email is valid according to the pattern and false otherwise.
        
        ^: Start of the string.
        a-zA-Z:  accepts lowercase and uppercase letters.
        0-9:  accepts numbers.
        ._%+-]+ : accepts special characters.
        +@ : The plus ensures that the next literal character (@) is included.
        After the @ it accepts the same as before the @.
        \\.: Matches a literal dot (.). The backslash is used to escape the dot.
        After the dot, only letters are accepted and it has to be between 2 - 6 characters long.
        $: End of string.
        */
        String emailValidation = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; 
        return email.matches(emailValidation);
    }
}