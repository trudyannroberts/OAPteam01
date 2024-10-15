package gui;

import javax.swing.*;
import java.awt.*;

public class HomePageGUI extends JFrame {
	protected JPanel contentPanel; 
	
	public HomePageGUI() {
        setTitle("Streaming Service Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

     //Navigation panel
        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.NORTH); 

        //Content panel 
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout()); // Use CardLayout for switching between content
        contentPanel.setBackground(new Color(240, 248, 255));
        add(contentPanel, BorderLayout.CENTER);
        
       
        showDefaultPage();
        
        setVisible(true);
        
	}
	
	 // Method to create the navigation bar
    private JPanel createNavPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        panel.setBackground(new Color(70, 130, 180));

        JButton homeButton = new JButton("Home");
        JButton browseButton = new JButton("Browse");
        JButton accountButton = new JButton("Account");
        JButton reviewButton = new JButton("Review");
        JButton historyButton = new JButton("History");
        
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

        
        
        homeButton.addActionListener(e -> showDefaultPage());
        browseButton.addActionListener(e -> showBrowsePage());
        accountButton.addActionListener(e -> showAccountPage());
        reviewButton.addActionListener(e -> showReviewPage());
        historyButton.addActionListener(e -> showHistoryPage());

       
        panel.add(homeButton);
        panel.add(browseButton);
        panel.add(accountButton);
        panel.add(reviewButton);
        panel.add(historyButton);

        return panel;
    }
    
    // Method to show the homepage
    protected void showDefaultPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Welcome to the Streaming Service", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Placeholder for showing the browse page
    protected void showBrowsePage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Browse Movies", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Placeholder for showing the account page
    protected void showAccountPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Manage Account", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Placeholder for showing the review page
    protected void showReviewPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Review Movies", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Placeholder for showing the history page
    protected void showHistoryPage() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Viewing History", SwingConstants.CENTER);
        label.setForeground(new Color(70, 130, 180)); 
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
 

    // Main method to launch the GUI
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(() -> {
            new HomePageGUI(); 
        });
    }
}
