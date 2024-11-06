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
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAddProfile = new JButton("Add Profile");
		btnAddProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateProfileDialog();
            }
        });

        // Add button to the AccountGUI frame
        JPanel panel = new JPanel();
        panel.add(btnAddProfile);
        getContentPane().add(panel);
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
}