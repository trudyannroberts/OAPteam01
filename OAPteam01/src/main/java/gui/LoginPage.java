package gui;

import userProfile.User;
import userProfile.UserAuthenticator;

import javax.swing.*;
import java.awt.*;

public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Media Streaming Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        // Username field
        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        frame.add(usernamePanel);

        // Password field
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        frame.add(passwordPanel);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        frame.add(buttonPanel);

        // Button actions
        loginButton.addActionListener(e -> logIn());
        registerButton.addActionListener(e -> registerUser());

        frame.setVisible(true);
    }

    private void logIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Authenticate the user
        User loggedInUser = UserAuthenticator.logInUser(username, password);

        if (loggedInUser != null) {
            // If login is successful, display the user's name
            JOptionPane.showMessageDialog(frame, "Welcome, " + loggedInUser.getFirstName() + "!");
            // Proceed with other actions such as showing user-specific content or navigating to the main screen
        } else {
            // Handle login failure
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        }
    }

    private void registerUser() {
        // Collect user details via input dialogs
        String firstName = JOptionPane.showInputDialog(frame, "Enter First Name:");
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name:");
        String email = JOptionPane.showInputDialog(frame, "Enter Email:");
        String username = JOptionPane.showInputDialog(frame, "Enter Username:");
        String password = JOptionPane.showInputDialog(frame, "Enter Password:");

        // Log inputs for debugging (optional)
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Create a User object with the collected details
        User newUser = new User(firstName, lastName, email, username, password);

        // Register the user via the UserAuthenticator
        if (UserAuthenticator.registerUser(newUser)) {
            JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.");
        } else {
            JOptionPane.showMessageDialog(frame, "Registration failed. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}