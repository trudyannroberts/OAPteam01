package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The HomePageGUI class represents the main window of the streaming service.
 * It extends BaseGUI and adds specific functionality for the home page.
 * 
 * @author Stine Andreassen Skrøder
 */
public class HomePageGUI extends BaseGUI {

	 /** The color used for the navigation bar and welcome text. */
    private static final Color NAV_BAR_COLOR = new Color(70, 130, 180); // Same color as navigation bar

    /**
     * Constructs a new HomePageGUI object, setting up the window properties
     * and displaying the default content.
     */
    public HomePageGUI() {
        super("Streaming Service Home");
        initializeHomePageContent();
        setVisible(true);
    }
    
    /**
     * Initializes the content for the home page.
     * This method sets up the layout, creates a welcome message,
     * and centers it on the page.
     */
    private void initializeHomePageContent() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(240, 248, 255)); // Set background color

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("Welcome to the streaming service");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(NAV_BAR_COLOR); // Set text color to match navigation bar
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Center the text horizontally

        // Add some padding around the label
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false); // Make it transparent
        paddingPanel.add(welcomeLabel, BorderLayout.CENTER);

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

        homePanel.add(centeringPanel, BorderLayout.CENTER);

        updateContentPanel(homePanel);
    }

   
}