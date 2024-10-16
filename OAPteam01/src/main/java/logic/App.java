package logic;

import javax.swing.SwingUtilities;
import gui.HomePageGUI;

public class App {

    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Use SwingUtilities to ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new HomePageGUI(); 
        });
    }
}
