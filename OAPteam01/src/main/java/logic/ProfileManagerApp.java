package logic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import db.DatabaseConnection;

public class ProfileManagerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Simulate user login (fetching userId from database)
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int userId = authenticateUser(username, password); // Fetch userId from DB based on login
        if (userId != -1) {
            ProfileManager profileManager = new ProfileManager(userId);

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Profile");
                System.out.println("2. Display Profiles");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        if (profileManager.getProfileCount() < 5) {
                            System.out.print("Enter profile name: ");
                            String name = scanner.nextLine();
                            System.out.print("Is this an adult profile? (yes/no): ");
                            String isAdultInput = scanner.nextLine();
                            boolean isAdult = isAdultInput.equalsIgnoreCase("yes");
                            if (isAdult) {
                                profileManager.addProfile(new AdultProfile(name));
                            } else {
                                profileManager.addProfile(new ChildrenProfile(name));
                            }
                        } else {
                            System.out.println("Maximum number of profiles reached.");
                        }
                        break;
                    case 2:
                        profileManager.displayProfiles();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("Invalid login. Please try again.");
        }
    }

    // Simulate database authentication
    public static int authenticateUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT staff_id FROM staff WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("staff_id");
            } else {
                return -1; // Authentication failed
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
