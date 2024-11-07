package launchApp;



import javax.swing.SwingUtilities;
import gui.HomePageGUI;
import gui.LoginPage;

/**
 * 
 * @author Stine Andreassen Skrøder
 */
public class App {
	 /**
     * Main method to launch the application.
     * 
     * @param args command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage(() -> {
                // This callback will be executed after a successful login
                new HomePageGUI();
            });
        });
    }
}
