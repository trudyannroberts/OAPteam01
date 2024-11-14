package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The BaseGUI class represents the base structure for GUI pages in the streaming service.
 * It includes common elements like the window setup and navigation bar.
 * 
 * @author Stine Andreassen Skrøder
 */
public class BaseGUI extends JFrame {
    
	/** The panel that holds the main content of the GUI. */
    protected JPanel contentPanel;

    /**
     * Constructs a new BaseGUI object, setting up the window properties and navigation bar.
     * 
     * @param title The title of the window
     */
    public BaseGUI(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create and add the navigation panel
        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.NORTH);

        // Initialize and configure the content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(240, 248, 255));
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates the navigation panel, which contains buttons for the different pages: Home, Browse and Account.
     * 
     * @return JPanel The constructed JPanel object for the navigation bar
     */
    protected JPanel createNavPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(70, 130, 180)); 

        JButton homeButton = new JButton("Homepage");
        JButton browseButton = new JButton("Browse Movies");
        JButton accountButton = new JButton("Account Page");
        JButton importButton = new JButton("Import Films");

        styleButton(homeButton);
        styleButton(browseButton);
        styleButton(accountButton);
        styleButton(importButton);

        homeButton.addActionListener(e -> showHomePage());
        browseButton.addActionListener(e -> showBrowsePage());
        accountButton.addActionListener(e -> showAccountPage());
        importButton.addActionListener(e -> showFilmImportPage());

        panel.add(homeButton);
        panel.add(browseButton);
        panel.add(accountButton);
        panel.add(importButton);
        return panel;
    }
    /**
     * Applies a consistent style to a button in the navigation panel.
     * 
     * @param button The JButton to be styled
     */
    protected void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(70, 130, 180));
    }
    /**
     * Displays the Home page of the application.
     * If the current page is not already the Home page, it creates a new HomePageGUI and disposes of the current window.
     */
    protected void showHomePage() {
        if (!(this instanceof HomePageGUI)) {
            new HomePageGUI().setVisible(true);
            this.dispose();
        }
    }
    /**
     * Displays the Browse page of the application.
     * If the current page is not already the Browse page, it creates a new BrowseMoviesPage and disposes of the current window.
     */
    protected void showBrowsePage() {
        if (!(this instanceof BrowseMoviesPage)) {
            new BrowseMoviesPage().setVisible(true);
            this.dispose();
        }
    }
    /**
     * Displays the Account page of the application.
     * If the current page is not already the Account page, it creates a new AccountGUI and disposes of the current window.
     */
    protected void showAccountPage() {
    	if (!(this instanceof AccountGUI)) {
    			new AccountGUI().setVisible(true);
    			this.dispose();
    	}
 }
    	/**
         * Displays the page that can mass import films to the application.
         * If the current page is not already the Film Import page, it creates a new FilmImportGUI and disposes of the current window.
         */
        protected void showFilmImportPage() {
        	if (!(this instanceof FilmImportGUI)) {
        			new FilmImportGUI().setVisible(true);
        			this.dispose();
    }
    	
    }
    /**
     * Updates the content panel with new content.
     * This method removes all components from the content panel, adds the new content, and refreshes the display.
     * 
     * @param newContent The new JPanel to be displayed in the content area
     */
    protected void updateContentPanel(JPanel newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}