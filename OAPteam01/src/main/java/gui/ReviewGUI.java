package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import review.Review;

/**
 * The ReviewGUI allows users to rate a movie after watching it.
 * It extends BaseGUI to include common GUI elements.
 * 
 * @author Stine Andreassen Skrøder
 */
public class ReviewGUI extends BaseGUI {
    private JButton submitButton;
    private String filmTitle;    

    public ReviewGUI(String filmTitle) {
        super("Rate the Movie"); // Call the constructor of BaseGUI with the title
        this.filmTitle = filmTitle;

        setSize(600, 400); 
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window

        // Initialize the review panel
        initializeReviewPanel();
        
        setVisible(true); // Make the frame visible after all components are added
    }

    @Override
    protected JPanel createNavPanel() {
        // Return an empty panel to avoid showing navigation buttons
        return new JPanel(); // No navigation bar for this GUI
    }

    private void initializeReviewPanel() {
        contentPanel.removeAll(); // Clear previous content
        
        JPanel reviewPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
        reviewPanel.setBackground(new Color(240, 248, 255)); // Use a light background color

        JLabel titleLabel = new JLabel("Rate " + filmTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Add title label at the top
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some vertical spacing
        reviewPanel.add(titleLabel, gbc);
        
        // Create a panel for radio buttons
        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(new Color(240, 248, 255));
        
        ButtonGroup group = new ButtonGroup();
        
        for (int i = 1; i <= 10; i++) {
            JRadioButton radioButton = new JRadioButton(String.valueOf(i));
            radioButton.setActionCommand(String.valueOf(i)); // Set action command for easy retrieval later
            group.add(radioButton);
            radioPanel.add(radioButton);
            
            if (i == 5) { // Default selection for the middle value
                radioButton.setSelected(true);
            }
        }

        // Centering the radio panel in the review panel
        gbc.gridy = 1; // Move to the next row in GridBagLayout
        reviewPanel.add(radioPanel, gbc);

        submitButton = new JButton("Submit Rating");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (group.getSelection() != null) { // Check if a button is selected
                    int rating = Integer.parseInt(group.getSelection().getActionCommand()); // Get selected value from radio buttons
                    
                    // Display confirmation message
                    JOptionPane.showMessageDialog(ReviewGUI.this,
                            "You rated \"" + filmTitle + "\" " + rating + "/10.",
                            "Rating Submitted",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    Review review = new Review(rating, filmTitle);
                    review.displayReview();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ReviewGUI.this, "Please select a rating before submitting.", "No Rating Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        gbc.gridy = 2; // Move to the next row for the button
        gbc.insets = new Insets(20, 0, 0, 0); // Add some spacing above the button
        reviewPanel.add(submitButton, gbc);
        
        updateContentPanel(reviewPanel); // Update the content panel with the review panel
    }    

}