package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import review.Review;
import review.ReviewHandler;
import review.ReviewManager;
import java.io.IOException;

/**
 * The ReviewGUI class represents a graphical user interface for rating movies.
 * It extends BaseGUI to include common GUI elements and provides functionality
 * for users to submit ratings for films they have watched.
 * 
 * @author Stine Andreassen Skrøder
 */
public class ReviewGUI extends BaseGUI {
	
    private JButton submitButton;
    private String filmTitle;
    private ReviewHandler reviewHandler;

    /**
     * Constructs a new ReviewGUI for a specific film.
     * 
     * @param filmTitle The title of the film to be rated
     */
    public ReviewGUI(String filmTitle) {
        super("Rate the Movie");
        this.filmTitle = filmTitle;
        this.reviewHandler = new ReviewManager(); // Initialize with ReviewManager, but use ReviewHandler interface

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeReviewPanel();
        
        setVisible(true);
    }
    /**
     * Overrides the createNavPanel method from BaseGUI.
     * This GUI does not require a navigation panel.
     * 
     * @return An empty JPanel
     */
    @Override
    protected JPanel createNavPanel() {
        return new JPanel(); // No navigation bar for this GUI
    }
    /**
     * Initializes the review panel with rating options and a submit button.
     * This method sets up the layout, creates radio buttons for ratings,
     * and adds a submit button with its action listener.
     */
    private void initializeReviewPanel() {
        contentPanel.removeAll();
        
        JPanel reviewPanel = new JPanel(new GridBagLayout());
        reviewPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Rate " + filmTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        reviewPanel.add(titleLabel, gbc);
        
        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(new Color(240, 248, 255));
        
        ButtonGroup group = new ButtonGroup();
        
        for (int i = 1; i <= 10; i++) {
            JRadioButton radioButton = new JRadioButton(String.valueOf(i));
            radioButton.setActionCommand(String.valueOf(i));
            group.add(radioButton);
            radioPanel.add(radioButton);
            
            if (i == 5) {
                radioButton.setSelected(true);
            }
        }

        gbc.gridy = 1;
        reviewPanel.add(radioPanel, gbc);

        submitButton = new JButton("Submit Rating");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (group.getSelection() != null) {
                    int rating = Integer.parseInt(group.getSelection().getActionCommand());
                    
                    try {
                        reviewHandler.saveReviewToFile(filmTitle, rating); // Use ReviewHandler interface
                        
                        JOptionPane.showMessageDialog(ReviewGUI.this,
                                "You rated \"" + filmTitle + "\" " + rating + "/10.",
                                "Rating Submitted",
                                JOptionPane.INFORMATION_MESSAGE);
                        
                        Review review = new Review(rating, filmTitle);
                        review.displayReview();
                        dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ReviewGUI.this,
                                "Error saving review: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ReviewGUI.this, 
                            "Please select a rating before submitting.", 
                            "No Rating Selected", 
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        reviewPanel.add(submitButton, gbc);
        
        updateContentPanel(reviewPanel);
    }
}