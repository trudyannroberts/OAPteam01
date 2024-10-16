package gui;

import logic.User;
import logic.UserAuthenticator;
import javax.swing.*;
import java.awt.*;

/**
 * The {@code LoginPage} class represents the GUI for the login page of a media streaming service.
 * Users can either log in with their credentials or register for a new account. After logging in, 
 * users should be able to select from a maximum of five profiles. If a user is new and has no profiles, 
 * they will be prompted to create one.
 * 
 * <p>This class utilizes {@link UserAuthenticator} to manage user authentication and profile handling. 
 * It makes use of Swing components for the graphical user interface.
 * 
 * <p><b>Note:</b> The profile selection feature is not yet implemented.
 * 
 * @author Trudy Ann Roberts
 */
public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructs a new {@code LoginPage} and initializes the GUI components.
     */
    public LoginPage() {
        initialize();
    }

    /**
     * Initializes the graphical user interface components of the login page.
     * This includes setting up the JFrame, adding text fields for username and password,
     * and creating buttons for login and registration.
     */
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

    /**
     * Handles the login action when the "Login" button is pressed. It retrieves the username and password 
     * from the input fields and authenticates the user using the {@link UserAuthenticator}.
     * If the login is successful, the user's first name is displayed in a welcome message.
     * Otherwise, an error message is shown.
     */
    private void logIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Authenticate the user
        User loggedInUser = UserAuthenticator.logInUser(username, password);

        if (loggedInUser != null) {
            // If login is successful, display the user's name
            JOptionPane.showMessageDialog(frame, "Welcome, " + loggedInUser.getFirstName() + "!");
            // TODO: Show user-specific content or navigate to the main screen
        } else {
            // Handle login failure
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        }
    }

    /**
     * Handles the registration action when the "Register" button is pressed. It prompts the user 
     * for their first name, last name, email, username, and password, and creates a new {@link User} object. 
     * The user is then registered using the {@link UserAuthenticator}.
     * If the registration is successful, a confirmation message is displayed.
     * Otherwise, an error message is shown.
     */
    private void registerUser() {
        // Collect user details via input dialogs
        String firstName = JOptionPane.showInputDialog(frame, "Enter First Name:");
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name:");
        String email = JOptionPane.showInputDialog(frame, "Enter Email:");
        String username = JOptionPane.showInputDialog(frame, "Enter Username:");
        String password = JOptionPane.showInputDialog(frame, "Enter Password:");

        // Create a User object with the collected details
        User newUser = new User(firstName, lastName, email, username, password);

        // Register the user via the UserAuthenticator
        if (UserAuthenticator.registerUser(newUser)) {
            JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.");
        } else {
            JOptionPane.showMessageDialog(frame, "Registration failed. Please try again.");
        }
    }

    /**
     * The main method that launches the {@code LoginPage} GUI.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
