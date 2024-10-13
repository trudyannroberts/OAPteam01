package gui;

import javax.swing.*;
import java.awt.*;

public class HomePageGUI extends JFrame {
	protected JPanel contentPanel; // Panel to display different content
	
	public HomePageGUI() {
        setTitle("Streaming Service Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

     // Create a navigation panel (top or side)
        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.NORTH); // Add the navigation bar at the top

        // Create a content panel where specific functionality will be displayed
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout()); // Use CardLayout for switching between content
        add(contentPanel, BorderLayout.CENTER);
        
        // Initialize the GUI with a default view (like homepage or dashboard)
        showDefaultPage();
        
	}
        
}
