package userProfile;

import db.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * The UserAuthenticator class is responsible for managing user authentication, 
 * registration, and profile loading operations. 
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
	    // Validate email format and show an error if it's invalid
	    if (!isValidEmail(user.getEmail())) {
	        JOptionPane.showMessageDialog(null, "Invalid email format.");
	        return false; // Exit registration due to invalid email
	    }

	    // Validate password strength
	    if (!isValidPassword(user.getPassword())) {
	        JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, and one number.");
	        return false; // Exit registration due to invalid password
	    }

	    // Hash the password
	    String hashedPassword = PasswordHasher.hashPassword(user.getPassword());

	    // Attempt to register the user in the database
	    int newUserId = insertUserIntoDatabase(user, hashedPassword);
	    
	    if (newUserId > 0) {
	        // Initialize ProfileManager and add a default profile if no profiles exist
	        ProfileManager profileManager = new ProfileManager(newUserId);

	        if (profileManager.getProfiles().isEmpty()) {  // Only create if no profiles exist for this user
	            UserProfile defaultProfile = new UserProfile(user.getFirstName(), UserProfile.ProfileType.ADULT);
	            profileManager.addProfile(defaultProfile);  // Save profile immediately
	        }
	        return true;
	    } else {
	        JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
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

	    // Retrieve user from the database based on username
	    User user = retrieveUserFromDatabase(username);

	    if (user != null) {
	        // Hash the input password and compare it with the stored hash
	        String hashedInputPassword = PasswordHasher.hashPassword(password);

	        if (hashedInputPassword.equals(user.getPassword())) {
	            Session.setCurrentUser(user);

	            // Load and display user profiles
	            ProfileManager profileManager = new ProfileManager(user.getUserId());
	            List<String> profiles = profileManager.getProfiles().stream()
	                                        .map(UserProfile::getProfileName)
	                                        .toList();

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
	            return user;
	        } else {
	            JOptionPane.showMessageDialog(null, "Invalid username or password.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Invalid username or password.");
	    }
	    return null;
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
        *It returns true if the email is valid according to the pattern and false otherwise.
        **/
        
        String emailValidation = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"; 
        return email.matches(emailValidation);
    }
    
    /**
     * Inserts a new user record into the database.
     * 
     * @param user The User object containing user details for registration.
     * @param hashedPassword The hashed password to store in the database.
     * @return The user ID of the newly inserted user if successful, or -1 if registration fails.
     */
    private static int insertUserIntoDatabase(User user, String hashedPassword) {
        String query = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) " +
                       "VALUES (?, ?, 1, ?, 1, 1, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, hashedPassword);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the user ID generated by the database
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the new user ID
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1; // Indicate failure to register the user
    }
    
    /**
     * Retrieves a User object from the database based on the provided username.
     * 
     * @param username The username of the user to retrieve.
     * @return A User object containing the user's details if found, or null if no user is found.
     */
    private static User retrieveUserFromDatabase(String username) {
        String query = "SELECT * FROM staff WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve user details and create a new User object
                String userId = resultSet.getString("staff_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String storedHashedPassword = resultSet.getString("password");

                user = new User(Integer.parseInt(userId), firstName, lastName, email, username, storedHashedPassword);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }
}