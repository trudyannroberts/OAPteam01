package gui;

import javax.swing.*;
import userProfile.ProfileManager;
import userProfile.ProfilePanel;
import userProfile.UserProfile;
import userProfile.Session;
import java.awt.*;
import java.util.List;

/**
 * @author Stian Sahlsten Rosvald
 * @author Trudy Ann Roberts
 */



public class AccountGUI extends BaseGUI{
	private static final long serialVersionUID = 1L;
	private ProfileManager profileManager;
	private JList<String> profileList;
	 
	/**
	 * @param title
	 */
	
    public AccountGUI() {
        super("Manage Account");
        
        // Initialize ProfileManager with the current user's ID
        int userId = Session.getCurrentUserId();
        profileManager = new ProfileManager(userId);
        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 240, 240)); // Set background color
        
        // Load profiles ad display them in the GUI
        loadProfiles();
        
        // Create a panel for the buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(240, 248, 255)); // Set background color

        // Buttons for the panel
		JButton btnAddProfile = new JButton("Add Profile");
		btnAddProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateProfileDialog();
            }
        });
		
		JButton btnShowProfiles = new JButton("Show Profiles");
		btnShowProfiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditProfileDialog();
			}
		});
        
		JButton btnDeleteProfile = new JButton("Delete Profile");
		btnShowProfiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedProfile();
			}
		});
		
		btnPanel.add(btnAddProfile);
		btnPanel.add(btnShowProfiles);
		btnPanel.add(btnDeleteProfile);
		
		contentPanel.add(btnPanel, BorderLayout.SOUTH);

    }
    
    
   // Load the current list of profiles connected to the account 
    private void loadProfiles() {
    	List<UserProfile> profiles = profileManager.getProfiles();
    	DefaultListModel<String> listModel = new DefaultListModel<>();
    	for (UserProfile profile : profiles) {
    		listModel.addElement(profile.getProfileName());
    	}
    	
    	if (profileList == null) {
    		profileList = new JList<>(listModel);
    		contentPanel.add(new JScrollPane(profileList), BorderLayout.CENTER);
    	} else {
    		profileList.setModel(listModel); // Update model if profileList is already inititalized
    	}
    	
    	contentPanel.revalidate(); // Refreshes the content panel
    	contentPanel.repaint();
    	
    }
    
    // initializes a pop-up for you to add profiles. 
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
            UserProfile.ProfileType profileType = isAdult? UserProfile.ProfileType.ADULT : UserProfile.ProfileType.CHILD;
            UserProfile newProfile = new UserProfile(profileName, profileType);
            
            boolean added = profileManager.addProfile(newProfile);
            if (added) {
                JOptionPane.showMessageDialog(this, "Profile saved successfully!");
                loadProfiles() ; 
            }
        }
    }
    
    // Method for editing a selected profile
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
                loadProfiles(); // Reload profiles to reflect changes
            }
        }
    }
    
    // Method for deleting a selected profile
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
                loadProfiles(); // Reload profiles to reflect changes
            }
        }
    }

}