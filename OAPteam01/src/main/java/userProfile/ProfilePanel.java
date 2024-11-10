// userProfile/ProfilePanel.java
package userProfile;

import javax.swing.*;
import java.awt.*;

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

    // Getter for profile name
    public String getProfileName() {
        return nameField.getText();
    }

    // Setter for profile name
    public void setProfileName(String name) {
        nameField.setText(name);
    }

    // Getter for adult checkbox
    public boolean isAdult() {
        return adultCheckBox.isSelected();
    }

    // Setter for adult checkbox
    public void setIsAdult(boolean isAdult) {
        adultCheckBox.setSelected(isAdult);
    }
}
