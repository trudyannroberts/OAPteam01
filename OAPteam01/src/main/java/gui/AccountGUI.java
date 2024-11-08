package gui;

import javax.swing.*;
import userProfile.ProfileManager;
import userProfile.ProfilePanel;
import userProfile.UserProfile;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Stian Sahlsten Rosvald
 */



public class AccountGUI extends BaseGUI{
	private ProfileManager profileManager;
	    /**
	 * @param title
	 */
	
    public AccountGUI() {
        super("Manage Account");
        initializeAccountGUIContent();
        setVisible(true);
    }
    
    /**
     * Initializes the content for the account page.
     * This method sets up the layout, creates the buttons,
     * and centers it on the page.
     */
    private void initializeAccountGUIContent() {
        JPanel accountGUIPanel = new JPanel(new BorderLayout());
        accountGUIPanel.setBackground(new Color(240, 248, 255)); // Set background color

        // Create two buttons
		JButton btnAddProfile = new JButton("Add Profile");
		btnAddProfile.setHorizontalAlignment(SwingConstants.LEADING);
		btnAddProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateProfileDialog();
            }
        });
		
		JButton btnShowProfiles = new JButton("Show Profiles");
		btnShowProfiles.setHorizontalAlignment(SwingConstants.TRAILING);
		btnShowProfiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEditProfileDialog();
			}
		});
        
        
        // Add some padding around the buttons
        JPanel paddingPanel = new JPanel(new FlowLayout());
        paddingPanel.setOpaque(false); // Make it transparent
        paddingPanel.add(btnAddProfile);
        paddingPanel.add(btnShowProfiles);

        // Use a panel with GridBagLayout to center the paddingPanel
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setOpaque(false); // Make it transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        centeringPanel.add(paddingPanel, gbc);

        accountGUIPanel.add(centeringPanel, BorderLayout.CENTER);

        updateContentPanel(accountGUIPanel);
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

            // Convert the profile type to ProfileType enum
            UserProfile.ProfileType profileType = isAdult ? UserProfile.ProfileType.ADULT : UserProfile.ProfileType.CHILD;

            // Create a new UserProfile object
            UserProfile newProfile = new UserProfile(profileName, profileType);

            // Attempt to add the profile via ProfileManager
            boolean added = profileManager.addProfile(newProfile);
            if (added) {
                JOptionPane.showMessageDialog(this, "Profile saved successfully!");
            }
        }
    }
    
    private void showEditProfileDialog() {
    	
    }
}