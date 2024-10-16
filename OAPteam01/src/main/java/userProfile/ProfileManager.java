package userProfile;

import javax.swing.*;

import userProfile.UserProfile.ProfileType;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Manages user profiles for a media streaming application. 
 * Handles loading and saving profiles, as well as adding new profiles and storing viewing history.
 * 
 * Profiles are stored locally in a CSV file, where each profile is associated with a specific user by user ID.
 * Each profile can have its own viewing history stored in separate files.
 * 
 * @author Trudy Ann Roberts
 */
public class ProfileManager {
    private static final int MAX_PROFILES = 5; // Maximum number of profiles per user
    private List<UserProfile> profiles = new ArrayList<>(); // In-memory storage of profiles
    private int userId; // The ID of the user (retrieved from the database)

    /**
     * Constructor that initializes the ProfileManager for a specific user.
     * It loads the profiles associated with this user from a local file.
     * 
     * @param userId The ID of the user whose profiles will be managed.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles from file upon initialization
    }

    /**
     * Adds a new profile for the user. The profile is saved locally to a file if successfully added.
     * Displays an error message if the maximum number of profiles (5) is reached.
     * 
     * @param profile The {@link userProfile} to be added.
     * @return true if the profile is added successfully, false if the maximum limit is reached.
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
     * Saves all the user's profiles to a local text file ("profiles.txt").
     * Each profile is stored in CSV format with the profile name, profile type (ADULT/CHILD), and user ID.
     */
    public void saveProfilesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("profiles.txt"))) {
            for (UserProfile profile : profiles) {
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
     * Loads the profiles associated with the current user from a local file ("profiles.txt").
     * Only profiles matching the current user's ID are loaded into memory.
     * The file stores profiles in CSV format: ProfileName, ProfileType, UserId.
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
                    ProfileType profileType = UserProfile.ProfileType.valueOf(profileTypeStr);
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
    
    /**
     * Displays a GUI dialog allowing the user to select one of their profiles.
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

    /**
     * Saves the viewing history of a profile to a specific file. 
     * Each profile has its own history file named "history_<ProfileName>.txt".
     * 
     * @param profile The {@link userProfile} whose viewing history is being saved.
     * @param filmTitle The title of the film being watched.
     * @param filmId The unique ID of the film.
     * @param durationWatched The duration the film was watched.
     */
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

    /**
     * Retrieves the viewing history of a profile from a specific file ("history_<ProfileName>.txt").
     * Each line in the file represents a record of a film watched by the profile.
     * 
     * @param profile The {@link userProfile} whose viewing history is being retrieved.
     * @return A list of strings representing the viewing history, with each entry being a record of a watched film.
     */
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
