package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The ProfilePanel class represents a GUI component for creating or managing a user profile.
 * It includes fields for entering a profile name and selecting whether the profile is for an adult.
 * 
 * @author Stian Sahlsten Rosvald
 */
public class ProfilePanel extends JPanel {
    private JTextField nameField;
    private JCheckBox adultCheckBox;

    /**
     * Constructs a new ProfilePanel with input fields for profile name and profile type.
     */
    public ProfilePanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        // Profile name field
        JLabel nameLabel = new JLabel("Profile Name:");
        nameField = new JTextField(15);
        add(nameLabel);
        add(nameField);

        // Adult checkbox
        adultCheckBox = new JCheckBox("Is Adult?");
        add(new JLabel("Profile Type:"));
        add(adultCheckBox);
    }

    /**
     * Gets the text entered in the profile name field.
     *
     * @return the profile name as a String.
     */
    public String getProfileName() {
        return nameField.getText();
    }

    /**
     * Checks whether the "Is Adult?" checkbox is selected.
     *
     * @return true if the profile is marked as an adult, false otherwise.
     */
    public boolean isAdult() {
        return adultCheckBox.isSelected();
    }
}