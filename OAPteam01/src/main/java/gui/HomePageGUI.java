package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The HomePageGUI class represents the main window of the streaming service.
 * It includes a navigation bar with buttons for Home, Browse, Account... ,
 * and a content panel that displays different pages based on user interaction. 
 * 
 * @author Stine Andreassen Skrøder
 */

public class HomePageGUI extends JFrame {
    
    /**
     * The content panel where different pages (e.g., Home, Browse, Account) are displayed.
     */
	
    protected JPanel contentPanel; 
    
    /**
     * Constructs a new HomePageGUI object, setting up the window properties, 
     * navigation bar, and default content.
     */
    
    public HomePageGUI() {
        setTitle("Streaming Service Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null); // Centers the window on the screen
        setLayout(new BorderLayout());

        // Create and add the navigation panel
        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.NORTH); 

        // Initialize and configure the content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout()); // Use CardLayout for switching between content
        contentPanel.setBackground(new Color(240, 248, 255));
        add(contentPanel, BorderLayout.CENTER);

        // Display the default home page
        showDefaultPage();
        
        setVisible(true);
    }

    /**
     * Creates the navigation panel, which contains buttons for different pages
     * such as Home, Browse, Account...
     * 
     * @return the constructed JPanel object for the navigation bar.
     */
    
    private JPanel createNavPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(70, 130, 180)); // Steel blue color

        // Create buttons for the navigation panel
        JButton homeButton = new JButton("Home");
        JButton browseButton = new JButton("Browse");
        JButton accountButton = new JButton("Account");
        JButton reviewButton = new JButton("Review");
        JButton historyButton = new JButton("History");

        // Style the buttons
        homeButton.setBackground(Color.WHITE);
        homeButton.setForeground(new Color(70, 130, 180)); 
        browseButton.setBackground(Color.WHITE);
        browseButton.setForeground(new Color(70, 130, 180));
        accountButton.setBackground(Color.WHITE);
        accountButton.setForeground(new Color(70, 130, 180));
        reviewButton.setBackground(Color.WHITE);
        reviewButton.setForeground(new Color(70, 130, 180));
        historyButton.setBackground(Color.WHITE);
        historyButton.setForeground(new Color(70, 130, 180));

        // Add action listeners to handle button clicks
        homeButton.addActionListener(e -> showDefaultPage());
        browseButton.addActionListener(e -> showBrowsePage());
        accountButton.addActionListener(e -> showAccountPage());
        reviewButton.addActionListener(e -> showReviewPage());
        historyButton.addActionListener(e -> showHistoryPage());

        // Add buttons to the navigation panel
        panel.add(homeButton);
        panel.add(browseButton);
        panel.add(accountButton);
        panel.add(reviewButton);
        panel.add(historyButton);

        return panel;
    }

    /**
     * Displays the default home page in the content panel. The default page contains
     * a welcome message.
     */
    
    protected void showDefaultPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Welcome to the Streaming Service", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Displays the browse page in the content panel.
     */
    protected void showBrowsePage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Browse Movies", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180));
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    

    /**
     * Displays the account management page in the content panel. 
     */
    protected void showAccountPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Manage Account", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Displays the review page in the content panel. 
     */
    protected void showReviewPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Review Movies", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Displays the viewing history page in the content panel.
     */
    protected void showHistoryPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Viewing History", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * The main method that launches the application by creating an instance of HomePageGUI.
     * 
     * @param args command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePageGUI(); 
        });
    }
}