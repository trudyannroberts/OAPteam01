/**
 * @author Trudy Ann Roberts
 * 
 * The ProfileManagerApp class allows users to manage their profiles. The class provides a menu 
 * where users can authenticate, add up to 5 profiles (either adult or children's), and display 
 * their profiles.
 */
public class ProfileManagerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to log in
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Authenticate the user and retrieve userId if credentials are valid
        int userId = authenticateUser(username, password);
        
        if (userId != -1) { // Check if authentication was successful
            ProfileManager profileManager = new ProfileManager(userId); // Initialize ProfileManager with userId

            // Display menu options for the user to manage profiles
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Profile");
                System.out.println("2. Display Profiles");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline after int input

                switch (choice) {
                    case 1:
                        // Allow the user to add a profile if less than 5 profiles exist
                        if (profileManager.getProfileCount() < 5) {
                            System.out.print("Enter profile name: ");
                            String name = scanner.nextLine();

                            System.out.print("Is this an adult profile? (yes/no): ");
                            String isAdultInput = scanner.nextLine();
                            boolean isAdult = isAdultInput.equalsIgnoreCase("yes");

                            // Add profile based on the user's input
                            if (isAdult) {
                                profileManager.addProfile(new AdultProfile(name));
                            } else {
                                profileManager.addProfile(new ChildrenProfile(name));
                            }
                        } else {
                            // Display message when maximum number of profiles is reached
                            System.out.println("Maximum number of profiles reached.");
                        }
                        break;

                    case 2:
                        // Display all profiles for the logged-in user
                        profileManager.displayProfiles();
                        break;

                    case 3:
                        // Exit the application
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        // Handle invalid input
                        System.out.println("Invalid option.");
                }
            }
        } else {
            // Notify user if login credentials are incorrect
            System.out.println("Invalid login. Please try again.");
        }
    }

    /**
     * Authenticates the user by checking username and password against the database.
     * 
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return The staff_id of the authenticated user, or -1 if authentication fails
     */
    public static int authenticateUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT staff_id FROM staff WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Return staff_id if authentication succeeds
                return rs.getInt("staff_id");
            } else {
                return -1; // Return -1 if authentication fails
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 in case of an exception
        }
    }
}
