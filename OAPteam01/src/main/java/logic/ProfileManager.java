package logic;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * ProfileManager class manages user profiles for the media streaming service application.
 * It allows adding, loading, and selecting user profiles. Each user can have a maximum of
 * five profiles, and the profiles are stored in a local text file.
 * 
 * The class provides methods for profile management, ensuring that profiles are
 * loaded into memory upon initialization and that updates are saved back to the file.
 * 
 * @author Trudy Ann Roberts
 */
public class ProfileManager {
    private static final int MAX_PROFILES = 5; // Maximum number of profiles allowed
    private List<UserProfile> profiles = new ArrayList<>(); // To hold user profiles in memory
    private int userId; // The ID of the user (fetched from the database)

    /**
     * Constructs a ProfileManager for the specified user.
     * Loads existing profiles from a file upon initialization.
     *
     * @param userId The ID of the user whose profiles are to be managed.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles from file upon initialization
    }

    /**
     * Adds a new profile for the logged-in user.
     * If the user already has the maximum number of profiles, an error message is displayed.
     *
     * @param profile The UserProfile object to be added.
     * @return true if the profile was successfully added; false otherwise.
     */
    public boolean addProfile(UserProfile profile) {
        if (profiles.size() < MAX_PROFILES) {
            profiles.add(profile);
            saveProfilesToFile(); // Save updated profiles to file
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Maximum number of profiles reached.");
            return false;
        }
    }

    /**
     * Saves all user profiles to a local text file associated with the user.
     * Each profile is stored in CSV format (ProfileName, ProfileType, UserId).
     */
    private void saveProfilesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("profiles.txt"))) {
            for (UserProfile profile : profiles) {
                String profileType = profile instanceof AdultProfile ? "Adult" : "Children";
                String line = profile.getProfileName() + "," + profileType + "," + userId;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the profiles associated with this user from the local file.
     * Only profiles that match the current userId are loaded into memory.
     * The file stores profiles in CSV format (ProfileName, ProfileType, UserId).
     */
    private void loadProfilesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("profiles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split the CSV line into components
                String profileName = parts[0];
                String profileType = parts[1];
                int storedUserId = Integer.parseInt(parts[2]);

                // Load only profiles for the current user (matching userId)
                if (storedUserId == userId) {
                    if ("Adult".equals(profileType)) {
                        profiles.add(new AdultProfile(profileName));
                    } else {
                        profiles.add(new ChildrenProfile(profileName));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Profile file not found, starting fresh.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays a dialog for the user to select one of their profiles.
     *
     * @return The name of the selected profile, or null if no profiles are available.
     */
    public String selectProfile() {
        if (profiles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No profiles available.");
            return null;
        }

        String[] profileNames = profiles.stream()
                                        .map(UserProfile::getProfileName)
                                        .toArray(String[]::new);

        return (String) JOptionPane.showInputDialog(
                null,
                "Select a profile:",
                "Profile Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                profileNames,
                profileNames[0]
        );
    }
 // Saving viewing history to file
    public void addToViewingHistory(UserProfile profile, String filmTitle, int filmId, int durationWatched) {
        String fileName = "history_" + profile.getProfileName() + ".txt"; // Each profile has its own history file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // Append to file
            String watchedOn = LocalDateTime.now().toString();
            String line = filmTitle + "," + filmId + "," + watchedOn + "," + durationWatched;
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving viewing history: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
 // Retrieve viewing history from file
    public List<String> getViewingHistory(UserProfile profile) {
        String fileName = "history_" + profile.getProfileName() + ".txt";
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line); // Add each line (record) to the history list
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No viewing history found for profile: " + profile.getProfileName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading viewing history: " + e.getMessage());
            e.printStackTrace();
        }
        return history;
    }
}
