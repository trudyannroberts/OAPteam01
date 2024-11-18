package gui;

import javax.swing.*;
import userProfile.ProfileManager;
import userProfile.UserProfile;
import userProfile.Session;
import java.awt.*;
import java.util.List;

/**
 * AccountGUI class for managing user profiles
 * 
 * @author Stian Sahlsten Rosvald
 */
public class AccountGUI extends BaseGUI {
    private ProfileManager profileManager;
    private JList<String> profileList;

    public AccountGUI() {
        super("Manage Account");
        
        int userId = Session.getCurrentUserId();
        profileManager = new ProfileManager(userId);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(240, 240, 240)); // Set background color

        // Load profiles and display them in the GUI
        loadProfiles();

        // Create a panel for the buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(240, 248, 255));

        // Create and add buttons to the panel
        JButton btnAddProfile = new JButton("Add Profile");
        btnAddProfile.addActionListener(e -> showCreateProfileDialog());

        JButton btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.addActionListener(e -> showEditProfileDialog());

        JButton btnDeleteProfile = new JButton("Delete Profile");
        btnDeleteProfile.addActionListener(e -> deleteSelectedProfile());

        btnPanel.add(btnAddProfile);
        btnPanel.add(btnEditProfile);
        btnPanel.add(btnDeleteProfile);

        contentPanel.add(btnPanel, BorderLayout.SOUTH);  // Adding button panel to the SOUTH of contentPanel
    }

    private void loadProfiles() {
        List<UserProfile> profiles = profileManager.getProfiles();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        profiles.forEach(profile -> listModel.addElement(profile.getProfileName()));

        if (profileList == null) {
            profileList = new JList<>(listModel);
            contentPanel.add(new JScrollPane(profileList), BorderLayout.CENTER);
        } else {
            profileList.setModel(listModel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCreateProfileDialog() {
        ProfilePanel profilePanel = new ProfilePanel();
        int result = JOptionPane.showConfirmDialog(
                this,
                profilePanel,
                "Create New Profile",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String newProfileName = profilePanel.getProfileName();
            boolean isAdult = profilePanel.isAdult();
            UserProfile.ProfileType profileType = isAdult ? UserProfile.ProfileType.ADULT : UserProfile.ProfileType.CHILD;
            UserProfile newProfile = new UserProfile(newProfileName, profileType);

            boolean added = profileManager.addProfile(newProfile);
            if (added) {
                JOptionPane.showMessageDialog(this, "Profile saved successfully!");
                loadProfiles();
            }
        }
    }

    private void showEditProfileDialog() {
        String selectedProfileName = profileList.getSelectedValue();
        if (selectedProfileName == null) {
            JOptionPane.showMessageDialog(this, "Please select a profile to edit.");
            return;
        }

        ProfilePanel profilePanel = new ProfilePanel();
        int result = JOptionPane.showConfirmDialog(
                this,
                profilePanel,
                "Edit Profile",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String newProfileName = profilePanel.getProfileName();
            boolean isAdult = profilePanel.isAdult();
            String profileType = isAdult ? "ADULT" : "CHILD";
            boolean edited = profileManager.editProfile(selectedProfileName, newProfileName, profileType);
            if (edited) {
                loadProfiles();
            }
        }
    }

    private void deleteSelectedProfile() {
        String selectedProfileName = profileList.getSelectedValue();
        if (selectedProfileName == null) {
            JOptionPane.showMessageDialog(this, "Please select a profile to delete.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this profile?",
                "Delete Profile",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean deleted = profileManager.deleteProfile(selectedProfileName);
            if (deleted) {
                loadProfiles();
            }
        }
    }
}
