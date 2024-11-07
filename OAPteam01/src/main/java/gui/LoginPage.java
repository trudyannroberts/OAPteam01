package gui;

import userProfile.User;
import userProfile.UserAuthenticator;
import javax.swing.*;

import userProfile.Session;

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
        
        // Set loginButton as default when Enter is pressed
        frame.getRootPane().setDefaultButton(loginButton);
        
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
        	 Session.setCurrentUser(loggedInUser); // Set the current user
            JOptionPane.showMessageDialog(frame, "Welcome, " + loggedInUser.getFirstName() + "!");
            frame.dispose(); // Close the login window
            onLoginSuccess.run();
            // Proceed with other actions such as showing user-specific content
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        }
    }

    private void registerUser() {
        // Create a new JFrame for registration
        JFrame registrationFrame = new JFrame("Register New User");
        registrationFrame.setSize(400, 400);
        registrationFrame.setLayout(new BorderLayout());
        registrationFrame.setLocationRelativeTo(frame); // Center relative to the login frame

        // Main Panel with AliceBlue Background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Register New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x4682B4));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20)); // Spacer

        // Method to create aligned fields
        mainPanel.add(createFieldPanel("First Name:", new JTextField(20)));
        mainPanel.add(createFieldPanel("Last Name:", new JTextField(20)));
        mainPanel.add(createFieldPanel("Email:", new JTextField(20)));
        mainPanel.add(createFieldPanel("Username:", new JTextField(20)));
        mainPanel.add(createFieldPanel("Password:", new JPasswordField(20)));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Style buttons to match the login page
        submitButton.setBackground(new Color(255, 255, 255));
        submitButton.setForeground(new Color(0x4682B4));
        submitButton.setFocusPainted(false);

        cancelButton.setBackground(new Color(255, 255, 255));
        cancelButton.setForeground(new Color(0x4682B4));
        cancelButton.setFocusPainted(false);

        // Button actions
        submitButton.addActionListener(e -> {
            // Collect input data
            String firstName = ((JTextField) ((JPanel) mainPanel.getComponent(2)).getComponent(1)).getText();
            String lastName = ((JTextField) ((JPanel) mainPanel.getComponent(3)).getComponent(1)).getText();
            String email = ((JTextField) ((JPanel) mainPanel.getComponent(4)).getComponent(1)).getText();
            String username = ((JTextField) ((JPanel) mainPanel.getComponent(5)).getComponent(1)).getText();
            String password = new String(((JPasswordField) ((JPanel) mainPanel.getComponent(6)).getComponent(1)).getPassword());

            // Create a User object with the collected details
            User newUser = new User(firstName, lastName, email, username, password);

            // Register the user via the UserAuthenticator
            if (UserAuthenticator.registerUser(newUser)) {
                JOptionPane.showMessageDialog(registrationFrame, "Registration successful! You can now log in.");
                registrationFrame.dispose(); // Close the registration window
            } else {
                JOptionPane.showMessageDialog(registrationFrame, "Registration failed. Please try again.");
            }
        });

        cancelButton.addActionListener(e -> registrationFrame.dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        // Add main panel to registration frame
        registrationFrame.add(mainPanel, BorderLayout.CENTER);
        registrationFrame.setVisible(true);
    }

    // Helper method to create field panels
    private JPanel createFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(255, 255, 255));
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(0x4682B4));
        label.setPreferredSize(new Dimension(100, 20)); // Ensure labels have the same width
        panel.add(label);
        panel.add(field);
        return panel;
    }
}