package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The BaseGUI class represents the base structure for GUI pages in the streaming service.
 * It includes common elements like the window setup and navigation bar.
 * 
 * @Author Stine Andreassen Skrøder
 */
public class BaseGUI extends JFrame {
    
    protected JPanel contentPanel;

    /**
     * Constructs a new BaseGUI object, setting up the window properties and navigation bar.
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
     * @return the constructed JPanel object for the navigation bar.
     */
    protected JPanel createNavPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(70, 130, 180)); 

        JButton homeButton = new JButton("Home");
        JButton browseButton = new JButton("Browse");
        JButton accountButton = new JButton("Account");

        styleButton(homeButton);
        styleButton(browseButton);
        styleButton(accountButton);

        homeButton.addActionListener(e -> showHomePage());
        browseButton.addActionListener(e -> showBrowsePage());
        accountButton.addActionListener(e -> showAccountPage());

        panel.add(homeButton);
        panel.add(browseButton);
        panel.add(accountButton);

        return panel;
    }

    protected void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(70, 130, 180));
    }

    protected void showHomePage() {
        if (!(this instanceof HomePageGUI)) {
            new HomePageGUI().setVisible(true);
            this.dispose();
        }
    }

    protected void showBrowsePage() {
        if (!(this instanceof BrowseMoviesPage)) {
            new BrowseMoviesPage().setVisible(true);
            this.dispose();
        }
    }

    protected void showAccountPage() {
        // Implement account page navigation when ready
        // For example:
        // if (!(this instanceof AccountPage)) {
        //     new AccountPage().setVisible(true);
        //     this.dispose();
        // }
    }

    protected void updateContentPanel(JPanel newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}