package gui;

import javax.swing.*;
import userProfile.ProfileManager;
import userProfile.ProfilePanel;
import userProfile.UserProfile;
import userProfile.Session;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccountGUI extends BaseGUI {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProfileManager profileManager;
    private JList<String> profileList;

    public AccountGUI() {
        super("Manage Account");
        
        // Initialize ProfileManager with the current user's ID
        int userId = Session.getCurrentUserId();
        profileManager = new ProfileManager(userId);
        
        contentPanel.setLayout(new BorderLayout());

        // Load profiles and display them in the GUI
        loadProfiles();

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton btnAddProfile = new JButton("Add Profile");
        btnAddProfile.addActionListener(e -> showCreateProfileDialog());
        
        JButton btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.addActionListener(e -> showEditProfileDialog());
        
        JButton btnDeleteProfile = new JButton("Delete Profile");
        btnDeleteProfile.addActionListener(e -> deleteSelectedProfile());

        buttonPanel.add(btnAddProfile);
        buttonPanel.add(btnEditProfile);
        buttonPanel.add(btnDeleteProfile);

        contentPanel.add(new JScrollPane(profileList), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProfiles() {
        List<UserProfile> profiles = profileManager.getProfiles();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (UserProfile profile : profiles) {
            listModel.addElement(profile.getProfileName());
        }
        profileList = new JList<>(listModel);
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
            String profileName = profilePanel.getProfileName();
            boolean isAdult = profilePanel.isAdult();
            UserProfile.ProfileType profileType = isAdult ? UserProfile.ProfileType.ADULT : UserProfile.ProfileType.CHILD;
            UserProfile newProfile = new UserProfile(profileName, profileType);

            boolean added = profileManager.addProfile(newProfile);
            if (added) {
                JOptionPane.showMessageDialog(this, "Profile created successfully.");
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
                JOptionPane.showMessageDialog(this, "Profile edited successfully.");
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
                JOptionPane.showMessageDialog(this, "Profile deleted successfully.");
                loadProfiles();
            }
        }
    }
}
