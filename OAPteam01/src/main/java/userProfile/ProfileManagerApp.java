package userProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import db.DatabaseConnection;

public class ProfileManagerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to log in
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Hash the password using the PasswordHasher class
        String hashedPassword = PasswordHasher.hashPassword(password);

        // Authenticate user with hashed password
        int userId = authenticateUser(username, hashedPassword); // Fetch userId from DB based on login
        System.out.println("User ID after authentication: " + userId);

        if (userId > 0) {
            ProfileManager profileManager = new ProfileManager(userId);
            System.out.println("ProfileManager created successfully.");

            // Display menu options for the user to manage profiles
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Profile");
                System.out.println("2. Display Profiles");
                System.out.println("3. Delete Profile");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline after int input

                switch (choice) {
                    case 1:
                        // Allow the user to add a profile if less than 5 profiles exist
                        if (profileManager.getProfiles().size() < 5) {
                            System.out.print("Enter profile name: ");
                            String name = scanner.nextLine();
                            System.out.print("Is this an adult profile? (yes/no): ");
                            String isAdultInput = scanner.nextLine();
                            UserProfile.ProfileType profileType = isAdultInput.equalsIgnoreCase("yes")
                                    ? UserProfile.ProfileType.ADULT
                                    : UserProfile.ProfileType.CHILD;
                            profileManager.addProfile(new UserProfile(name, profileType));
                        } else {
                            System.out.println("Maximum number of profiles reached.");
                        }
                        break;
                    case 2:
                        // Display all profiles for the logged-in user
                        if (profileManager.getProfiles().isEmpty()) {
                            System.out.println("No profiles found.");
                        } else {
                            System.out.println("Profiles:");
                            profileManager.getProfiles().forEach(profile -> {
                                System.out.println(profile.getProfileName() + " (" + profile.getProfileType() + ")");
                            });
                        }
                        break;
                    case 3:
                        // Allow the user to delete a profile
                        System.out.print("Enter the name of the profile to delete: ");
                        String profileNameToDelete = scanner.nextLine();
                        if (profileManager.deleteProfile(profileNameToDelete)) {
                            System.out.println("Profile '" + profileNameToDelete + "' deleted.");
                        } else {
                            System.out.println("Profile '" + profileNameToDelete + "' not found.");
                        }
                        break;
                    case 4:
                        // Exit the application
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        // Handle invalid input
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid login. Please try again.");
        }

}


    // Authenticates the user by checking username and hashed password against the database
    private static int authenticateUser(String username, String hashedPassword) {
        int userId = -1; // Default to -1 for failure
        System.out.println("Attempting to authenticate user: " + username);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT staff_id FROM staff WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    userId = resultSet.getInt("staff_id"); // Retrieve the user ID
                    System.out.println("Authentication successful. Staff ID: " + userId);
                } else {
                    System.out.println("Authentication failed. No matching user found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error message or handle exception
        }

        return userId; // Returns -1 if no match is found
    }
}
