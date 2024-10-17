package gui;

import userProfile.ProfileManager;
import userProfile.UserProfile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewMyProfile extends JFrame {
    private ProfileManager profileManager;
    private JList<String> profileList; // Displays the profiles
    private DefaultListModel<String> listModel; // Model for the JList
    private JTextField profileNameField; // Field for entering profile name
    private JTextField profileTypeField; // Field for entering profile type

    /**
     * Constructs the ViewMyProfile GUI.
     *
     * @param profileManager The ProfileManager instance to manage user profiles.
     */
    public ViewMyProfile(ProfileManager profileManager) {
        this.profileManager = profileManager;
        setTitle("View My Profiles");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // List model to hold profile names
        listModel = new DefaultListModel<>();
        profileList = new JList<>(listModel);
        profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Add a JScrollPane to the list
        JScrollPane scrollPane = new JScrollPane(profileList);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for adding/editing profiles
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Profile Name:"));
        profileNameField = new JTextField();
        inputPanel.add(profileNameField);

        inputPanel.add(new JLabel("Profile Type:"));
        profileTypeField = new JTextField();
        inputPanel.add(profileTypeField);

        add(inputPanel, BorderLayout.NORTH);

        // Button panel for actions
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Profile");
        JButton editButton = new JButton("Edit Profile");
        JButton deleteButton = new JButton("Delete Profile");
        JButton viewHistoryButton = new JButton("View History");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewHistoryButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProfile();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProfile();
            }
        });

        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProfileHistory();
            }
        });

        updateProfileList(); // Update the profile list
    }

    /**
     * Updates the profile list display.
     */
    private void updateProfileList() {
        listModel.clear();
        for (UserProfile profile : profileManager.getProfiles()) {
            listModel.addElement(profile.getProfileName());
        }
    }

    /**
     * Adds a new profile based on the input fields.
     */
    private void addProfile() {
        String name = profileNameField.getText().trim();
        String type = profileTypeField.getText().trim().toUpperCase(); // Convert to uppercase for enum matching
        if (!name.isEmpty() && !type.isEmpty()) {
            UserProfile.ProfileType profileType;
            try {
                profileType = UserProfile.ProfileType.valueOf(type); // Convert string to enum
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid profile type. Please enter ADULT or CHILD.");
                return;
            }
            
            UserProfile newProfile = new UserProfile(name, profileType);
            if (profileManager.addProfile(newProfile)) {
                updateProfileList();
                clearInputFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter both name and type.");
        }
    }

    /**
     * Edits the selected profile's name or type.
     */
    private void editProfile() {
        String selectedProfileName = profileList.getSelectedValue();
        if (selectedProfileName != null) {
            String newName = profileNameField.getText().trim();
            String newType = profileTypeField.getText().trim().toUpperCase();
            if (!newName.isEmpty() && !newType.isEmpty()) {
                UserProfile.ProfileType profileType;
                try {
                    profileType = UserProfile.ProfileType.valueOf(newType);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Invalid profile type. Please enter ADULT or CHILD.");
                    return;
                }
                for (UserProfile profile : profileManager.getProfiles()) {
                    if (profile.getProfileName().equals(selectedProfileName)) {
                        profile.setProfileName(newName);
                        profile.setProfileType(profileType);
                        profileManager.saveProfilesToFile(); // Save changes
                        updateProfileList();
                        clearInputFields();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both name and type.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a profile to edit.");
        }
    }


    /**
     * Deletes the selected profile.
     */
    private void deleteProfile() {
        String selectedProfileName = profileList.getSelectedValue();
        if (selectedProfileName != null) {
            profileManager.deleteProfile(selectedProfileName);
            updateProfileList();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a profile to delete.");
        }
    }

    /**
     * Displays the viewing history for the selected profile.
     */
    private void viewProfileHistory() {
        String selectedProfileName = profileList.getSelectedValue();
        if (selectedProfileName != null) {
            UserProfile profile = profileManager.getProfiles().stream()
                    .filter(p -> p.getProfileName().equals(selectedProfileName))
                    .findFirst()
                    .orElse(null);
            if (profile != null) {
                List<String> history = profileManager.getViewingHistory(profile);
                StringBuilder historyBuilder = new StringBuilder();
                for (String record : history) {
                    historyBuilder.append(record).append("\n");
                }
                JTextArea textArea = new JTextArea(historyBuilder.toString());
                textArea.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Viewing History", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a profile to view history.");
        }
    }

    /**
     * Clears the input fields.
     */
    private void clearInputFields() {
        profileNameField.setText("");
        profileTypeField.setText("");
    }

    public static void main(String[] args) {
        // Example usage of ProfileManager with dummy user ID
        ProfileManager profileManager = new ProfileManager(1);
        ViewMyProfile viewMyProfile = new ViewMyProfile(profileManager);
        viewMyProfile.setVisible(true);
    }
}
