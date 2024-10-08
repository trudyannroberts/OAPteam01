

package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import db.DatabaseConnection;

public class UserAuthenticator {
    
    /**
     * This method will register a new user by prompting the user for info then an SQL query to insert the user data.
     * @param firstName is the users first name
     * @param lastName is the users last name
     * @param email is the users email  ----- HERE I NEED TO ADD CONSTRAINTS FOR A VALID EMAIL
     *  @param username is a username of the user's choice
     *  @param password ---- NEEDS CONSTRAINTS (capital letters, minimum 8 characters, and numbers)
     *  @param addressId has to be added because it is NOT NULL in the db. 
     */
    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);

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
     
      
        String query = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) " +
                       "VALUES (?, ?, 1, ?, 1, 1, ?, ?, NOW())";
        /**
         * Calling the Connection method from the DatabaseConnection class. 
         * The prepared statement sets the first parameter in the SQL query to the value firstName.
         */
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * An already registered user can log into the system with a username and password.
     * 
     */
    public static void loginUser() {
        Scanner scanner = new Scanner(System.in);

       
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        
        String query = "SELECT * FROM staff WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            var resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful! Welcome, " + resultSet.getString("first_name") + "!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param args. A main method where the user can choose to either register or log in.
     * If successfull, you will be greeted by your first name. If the username or password is not in the db,
     * the user will be informed that the user either doesn't exist or the password is wrong.
     * HERE WE HAVE TO CREATE BUTTONS.
     */

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            registerUser();
        } else if (option == 2) {
            loginUser();
        } else {
            System.out.println("The user does not exist or the password is wrong.");
        }
    }
}