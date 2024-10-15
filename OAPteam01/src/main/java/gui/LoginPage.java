package gui;

import logic.UserAuthenticator;

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

        UserAuthenticator.logInUser(username, password);
    }

    private void registerUser() {
        String firstName = JOptionPane.showInputDialog(frame, "Enter First Name:");
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name:");
        String email = JOptionPane.showInputDialog(frame, "Enter Email:");
        String username = JOptionPane.showInputDialog(frame, "Enter Username:");
        String password = JOptionPane.showInputDialog(frame, "Enter Password:");

        // Log inputs for debugging
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if (UserAuthenticator.registerUser(firstName, lastName, email, username, password)) {
            JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
