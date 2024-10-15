package logic;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class ProfileManager {
    private static final int MAX_PROFILES = 5;
    private List<UserProfile> profiles = new ArrayList<>(); // To hold user profiles in memory
    private int userId; // The ID of the user (fetched from the database)

    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles from file upon initialization
    }

    // Add a new profile for the logged-in user via GUI
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

    // Save profiles to a local text file (linked to the user)
    public void saveProfilesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("profiles.txt"))) {
            for (UserProfile profile : profiles) {
                // Save the profile as its name, type (ADULT/CHILD), and the userId
                String line = profile.getProfileName() + "," + profile.getProfileType() + "," + userId;
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
     * The file stores profiles in CSV format (ProfileName, ProfileType, UserId).
     * Only profiles that match the current userId are loaded into memory.
     */
    private void loadProfilesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("profiles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split the CSV line into components
                String profileName = parts[0];
                String profileTypeStr = parts[1];
                int storedUserId = Integer.parseInt(parts[2]);

                // Load only profiles for the current user (matching userId)
                if (storedUserId == userId) {
                    UserProfile.ProfileType profileType = UserProfile.ProfileType.valueOf(profileTypeStr);
                    profiles.add(new UserProfile(profileName, profileType)); // Create profile with type
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Profile file not found, starting fresh.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Display profile selection dialog in GUI
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
