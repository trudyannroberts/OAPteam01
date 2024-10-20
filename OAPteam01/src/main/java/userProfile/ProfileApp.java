import db.DatabaseConnection;
import userProfile.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProfileApp {
    private Map<String, String> users; // Maps username to hashed password
    private Map<String, List<UserProfile>> userProfiles; // Maps userId to their profiles
    private String currentUserId;
    private PasswordHasher passwordHasher; // Assume you have a PasswordHasher class

    public ProfileApp() {
        users = new HashMap<>();
        userProfiles = new HashMap<>();
        passwordHasher = new PasswordHasher(); // Initialize your password hasher
        loadUsers(); // Load users from the database
        ProfileManager.loadProfilesFromFile(); // Load profiles from a file or database
    }

    private void loadUsers() {
        // Load users from the database
        String query = "SELECT username, password FROM users"; // Adjust this query to match your table structure

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String hashedPassword = resultSet.getString("password"); // Get the hashed password
                users.put(username, hashedPassword); // Store username and hashed password in the map
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }

    public boolean login(String username, String password) {
        if (users.containsKey(username)) {
            String hashedPassword = users.get(username); // Get the stored hashed password
            // Check if the hashed input password matches the stored hashed password
            if (passwordHasher.checkPassword(password, hashedPassword)) {
                currentUserId = username; // Use username as userId for simplicity
                return true;
            }
        }
        return false;
    }

    public void addProfile(String profileName, UserProfile.ProfileType profileType) {
        if (currentUserId == null) {
            System.out.println("Please log in first.");
            return;
        }

        UserProfile newProfile = new UserProfile(profileName, profileType);
        userProfiles.computeIfAbsent(currentUserId, k -> new ArrayList<>()).add(newProfile);
        System.out.println("Profile added: " + newProfile.getProfileName());
    }

    public void viewProfiles() {
        if (currentUserId == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<UserProfile> profiles = userProfiles.get(currentUserId);
        if (profiles != null && !profiles.isEmpty()) {
            for (UserProfile profile : profiles) {
                System.out.println(profile.getProfileName() + " (" + profile.getProfileType() + ")");
            }
        } else {
            System.out.println("No profiles found for user: " + currentUserId);
        }
    }

    public void deleteProfile(String profileName) {
        if (currentUserId == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<UserProfile> profiles = userProfiles.get(currentUserId);
        if (profiles != null) {
            profiles.removeIf(profile -> profile.getProfileName().equals(profileName));
            System.out.println("Profile deleted: " + profileName);
        } else {
            System.out.println("No profiles found for user: " + currentUserId);
        }
    }

    public void editProfile(String oldName, String newName, UserProfile.ProfileType profileType) {
        if (currentUserId == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<UserProfile> profiles = userProfiles.get(currentUserId);
        if (profiles != null) {
            for (UserProfile profile : profiles) {
                if (profile.getProfileName().equals(oldName)) {
                    profile.setProfileName(newName);
                    profile.setProfileType(profileType);
                    System.out.println("Profile updated: " + profile.getProfileName());
                    return;
                }
            }
            System.out.println("Profile not found: " + oldName);
        } else {
            System.out.println("No profiles found for user: " + currentUserId);
        }
    }

    public static void main(String[] args) {
        ProfileApp app = new ProfileApp();
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (app.login(username, password)) {
            System.out.println("Login successful!");

            // Example usage
            app.addProfile("John's Profile", UserProfile.ProfileType.ADULT);
            app.addProfile("Jane's Profile", UserProfile.ProfileType.CHILD);
            app.viewProfiles();
            app.editProfile("Jane's Profile", "Jane Doe", UserProfile.ProfileType.CHILD);
            app.deleteProfile("John's Profile");
            app.viewProfiles();
        } else {
            System.out.println("Invalid username or password.");
        }

        scanner.close();
    }
}
