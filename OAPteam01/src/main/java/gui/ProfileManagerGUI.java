package gui;

import javax.swing.*;

import userProfile.ProfileManager;
import userProfile.UserProfile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileManagerGUI {
    private ProfileManager profileManager;
    private JFrame frame;
    private JTextField profileNameField;
    private JPasswordField passwordField;
    private JComboBox<UserProfile.ProfileType> profileTypeComboBox;

    public ProfileManagerGUI(int userId) {
        profileManager = new ProfileManager(userId);
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Profile Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Panel for profile actions
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(0, 1));

        // Profile name input
        profileNameField = new JTextField(20);
        actionPanel.add(new JLabel("Profile Name:"));
        actionPanel.add(profileNameField);

        // Profile type selection
        profileTypeComboBox = new JComboBox<>(UserProfile.ProfileType.values());
        actionPanel.add(new JLabel("Profile Type:"));
        actionPanel.add(profileTypeComboBox);

        // Password input
        passwordField = new JPasswordField(20);
        actionPanel.add(new JLabel("Password:"));
        actionPanel.add(passwordField);

        // Buttons
        JButton addButton = new JButton("Add Profile");
        JButton deleteButton = new JButton("Delete Profile");
        JButton loginButton = new JButton("Log In");

        actionPanel.add(addButton);
        actionPanel.add(deleteButton);
        actionPanel.add(loginButton);

        frame.add(actionPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String profileName = profileNameField.getText();
                String password = new String(passwordField.getPassword());
                UserProfile.ProfileType profileType = (UserProfile.ProfileType) profileTypeComboBox.getSelectedItem();
                UserProfile newProfile = new UserProfile(profileName, profileType, password);

                if (profileManager.addProfile(newProfile)) {
                    JOptionPane.showMessageDialog(frame, "Profile added successfully!");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String profileName = profileNameField.getText();
                if (profileManager.deleteProfile(profileName)) {
                    JOptionPane.showMessageDialog(frame, "Profile deleted successfully!");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String profileName = profileNameField.getText();
                String password = new String(passwordField.getPassword());
                if (profileManager.logIn(profileName, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // You may replace 1 with the actual user ID.
        SwingUtilities.invokeLater(() -> new ProfileManagerGUI(1));
    }
}
