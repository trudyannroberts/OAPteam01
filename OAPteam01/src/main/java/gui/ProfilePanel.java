package gui;

import javax.swing.*;
import java.awt.*;

/**
 * 
 */
public class ProfilePanel extends JPanel {
    private JTextField nameField;
    private JCheckBox adultCheckBox;

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

    public String getProfileName() {
        return nameField.getText();
    }

    public boolean isAdult() {
        return adultCheckBox.isSelected();
    }
}