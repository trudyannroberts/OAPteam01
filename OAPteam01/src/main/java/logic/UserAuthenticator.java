package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import db.DatabaseConnection;

public class UserAuthenticator {
    
    /**
     * Registers a new user by prompting the user for their details and then inserting the information into the database.
     * 
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param email The user's email address. 
     *               TODO: Add validation to ensure the email is in a valid format.
     * @param username A unique username chosen by the user.
     * @param password The user's password.
     *                 TODO: Add constraints for password validation (e.g., minimum 8 characters, at least one uppercase letter, and one number).
     * @param addressId Address ID is required as it cannot be NULL in the database.
     *                  Currently set to 1 as a placeholder. Adjust according to the actual implementation.
     */
    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for registration details
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        /**
         * SQL query to insert the user's data into the 'staff' table.
         * Placeholder values for address_id (set to 1), store_id, and active status are used.
         * TODO: Ensure valid values for address_id are provided when available.
         */
        String query = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) " +
                       "VALUES (?, ?, 1, ?, 1, 1, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            // Set parameters in the SQL query based on user input
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            
            int rowsAffected = pstmt.executeUpdate();  // Execute the query and check how many rows were affected
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            // Handle and print specific SQL errors for debugging
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs in an already registered user by validating their username and password.
     * 
     * Prompts the user for their username and password, then checks if the credentials match 
     * an entry in the 'staff' table.
     */
    public static void loginUser() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for login details
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        /**
         * SQL query to validate user credentials.
         * If a matching entry is found in the 'staff' table, login is successful.
         */
        String query = "SELECT * FROM staff WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            // Set parameters for the SQL query based on user input
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            var resultSet = pstmt.executeQuery();  // Execute the query and get the result
            if (resultSet.next()) {
                // If a matching record is found, login is successful
                System.out.println("Login successful! Welcome, " + resultSet.getString("first_name") + "!");
            } else {
                // If no match, inform the user
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            // Handle and print specific SQL errors for debugging
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main method to allow the user to choose between registering or logging in.
     * 
     * Presents the user with two options: 
     * 1. Register a new account.
     * 2. Log in to an existing account.
     * TODO: Implement a GUI with buttons to replace the console-based interaction.
     * If login is successful, the user is greeted by their first name.
     * If registration or login fails, appropriate error messages are displayed.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Display options to the user
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();  // Clear the buffer after integer input

        // Call the appropriate method based on the user's choice
        if (option == 1) {
            registerUser();
        } else if (option == 2) {
            loginUser();
        } else {
            System.out.println("Invalid option. Please choose 1 or 2.");
        }
    }
}
