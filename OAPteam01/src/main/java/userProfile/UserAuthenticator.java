package userProfile;

import javax.swing.*;
import db.UserDAO;
import java.util.List;

/**
 * The UserAuthenticator class manages user authentication, registration, and profile loading operations.
 * This includes validating user details, registering new users, logging in, and loading user profiles.
 * 
 * Upon registration, user details are stored in the database after validation. On successful login,
 * user profiles are loaded and selected. This class interacts with the database through the UserDAO class.
 * 
 * @author Trudy
 */
public class UserAuthenticator {

    /**
     * Registers a new user in the system by validating the email and password,
     * ensuring a unique username, and inserting their information into the database.
     *
     * @param user The User object containing the user's registration details.
     * @return true if the user was successfully registered, false otherwise.
     */
    public static boolean registerUser(User user) {
        boolean isValidEmail = validateEmail(user);
        if (!isValidEmail) return false;

        boolean isValidPassword = validatePassword(user);
        if (!isValidPassword) return false;
        
        boolean isUniqueUsername = validateUser(user);
        if (!isUniqueUsername) return false;

        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
        int userId = UserDAO.insertUserIntoDatabase(user, hashedPassword);

        if (userId > 0) {
            ProfileManager profileManager = new ProfileManager(userId);
            UserProfile defaultProfile = new UserProfile(user.getFirstName(), UserProfile.ProfileType.ADULT);
            profileManager.addProfile(defaultProfile);
            JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
            return false;
        }
    }

    /**
     * Logs in a user by verifying their username and password against stored credentials in the database.
     * If login is successful, the user’s profiles are loaded and can be selected.
     *
     * @param username The username provided by the user for login.
     * @param password The password provided by the user for login.
     * @return A User object if the login is successful, or null otherwise.
     */
    public static User logInUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password must be provided.");
            return null;
        }

        User user = UserDAO.retrieveUserFromDatabase(username);

        if (user != null) {
            String hashedInputPassword = PasswordHasher.hashPassword(password);
            if (hashedInputPassword.equals(user.getPassword())) {
                Session.setCurrentUser(user);
                loadUserProfile(user);
                return user;
            }
        }
        JOptionPane.showMessageDialog(null, "Invalid username or password.");
        return null;
    }

    /**
     * Loads user profiles associated with the given user and prompts the user to select one.
     *
     * @param user The logged-in user whose profiles are to be loaded.
     */
    private static void loadUserProfile(User user) {
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
    }

    /**
     * Validates the user's email format using a regular expression.
     *
     * @param user The User object whose email is to be validated.
     * @return true if the email format is valid, false otherwise.
     */
    private static boolean validateEmail(User user) {
        while (!isValidEmail(user.getEmail())) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            String newEmail = JOptionPane.showInputDialog("Please enter a valid email:");
            if (newEmail == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                return false;
            }
            user.setEmail(newEmail);
        }
        return true;
    }

    /**
     * Validates the user's password to ensure it meets the security requirements.
     *
     * @param user The User object whose password is to be validated.
     * @return true if the password meets the security criteria, false otherwise.
     */
    private static boolean validatePassword(User user) {
        while (!isValidPassword(user.getPassword())) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, and one number.");
            String newPassword = JOptionPane.showInputDialog("Please enter a valid password:");
            if (newPassword == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                return false;
            }
            user.setPassword(newPassword);
        }
        return true;
    }

    /**
     * Ensures that the username for the user is unique by checking against existing usernames in the database.
     *
     * @param user The User object containing the username to be validated.
     * @return true if the username is unique, false if it already exists.
     */
    private static boolean validateUser(User user) {
        while (!isUniqueUsername(user.getUsername())) {
            JOptionPane.showMessageDialog(null, "Username is not available.");
            String newUsername = JOptionPane.showInputDialog("Please enter a different username:");
            if (newUsername == null || newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                return false;
            }
            user.setUsername(newUsername);
        }
        return true;
    }

    /**
     * Checks if a username is unique in the database.
     *
     * @param username The username to be checked for uniqueness.
     * @return true if no existing user has the specified username, false otherwise.
     */
    public static boolean isUniqueUsername(String username) {
        User user = UserDAO.retrieveUserFromDatabase(username);
        return (user == null); // Returns true if no user with that username exists
    }

    /**
     * Validates the email format using a regular expression pattern.
     *
     * @param email The email to be validated.
     * @return true if the email format is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailValidation = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailValidation);
    }

    /**
     * Validates the password to ensure it meets security requirements.
     *
     * @param password The password to be validated.
     * @return true if the password meets criteria, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) uppercaseCount++;
            else if (Character.isLowerCase(ch)) lowercaseCount++;
            else if (Character.isDigit(ch)) digitCount++;
        }

        return uppercaseCount >= 1 && lowercaseCount >= 1 && digitCount >= 1 && password.length() >= 8;
    }
}
