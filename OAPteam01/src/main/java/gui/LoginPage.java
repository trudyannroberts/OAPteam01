package gui;

import userProfile.User;
import userProfile.UserAuthenticator;
import javax.swing.*;
import java.awt.*;

public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Runnable onLoginSuccess;

    public LoginPage(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
        initialize();
    }

    private void initialize() {
        // Frame setup
        frame = new JFrame("Media Streaming Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // to make sure the window is in the middle!

        // Main Panel with AliceBlue Background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255)); //  background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around main panel
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Spacing between elements

        // Title Label
        JLabel titleLabel = new JLabel("Login to Streaming Service", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x4682B4)); //  text
        mainPanel.add(titleLabel);

        // Username field
        JPanel usernamePanel = new JPanel();
        usernamePanel.setBackground(new Color(255, 255, 255)); //  background
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(0x4682B4)); //  color for labels
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);

        // Password field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(new Color(255, 255, 255)); //  background
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(0x4682B4)); // color for labels
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255)); 
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Style buttons with background similar to Steelblue and SteelBlue text
        loginButton.setBackground(new Color(255, 255, 255)); 
        loginButton.setForeground(new Color(0x4682B4)); 
        loginButton.setFocusPainted(false);

        registerButton.setBackground(new Color(255, 255, 255)); 
        registerButton.setForeground(new Color(0x4682B4)); 
        registerButton.setFocusPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel);

        // Button actions
        loginButton.addActionListener(e -> logIn());
        registerButton.addActionListener(e -> registerUser());

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private void logIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Authenticate the user by comparing the hashed password
        User loggedInUser = UserAuthenticator.logInUser(username, password);

        if (loggedInUser != null) {
            JOptionPane.showMessageDialog(frame, "Welcome, " + loggedInUser.getFirstName() + "!");
            frame.dispose(); // Close the login window
            onLoginSuccess.run();
            // Proceed with other actions such as showing user-specific content
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        }
    }

    private void registerUser() {
        // Collect user details via input dialogs
        String firstName = JOptionPane.showInputDialog(frame, "Enter First Name:");
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name:");
        String email = JOptionPane.showInputDialog(frame, "Enter Email:");
        String username = JOptionPane.showInputDialog(frame, "Enter Username:");
        
        // Prompt for password
        JPasswordField passwordField = new JPasswordField(20);
        int option = JOptionPane.showConfirmDialog(frame, passwordField, "Enter Password:", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

            // Create a User object with the collected details
            User newUser = new User(firstName, lastName, email, username, password);

            // Register the user via the UserAuthenticator
            if (UserAuthenticator.registerUser(newUser)) {
                JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.");
            } else {
                JOptionPane.showMessageDialog(frame, "Registration failed. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Registration cancelled.");
        }
    }
}
