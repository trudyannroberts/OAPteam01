package userProfile;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The ProfileManager class is responsible for managing user profiles. 
 * It allows users to create up to five profiles, each of which can be saved to and loaded from a file. 
 * Profiles can be added, selected, deleted, and their viewing history can be recorded and retrieved.
 * The class interacts with a local file system to persist user profiles and their viewing history.
 * 
 * A profile is associated with a UserProfile object, which contains information such as 
 * the profile's name and type (e.g., Adult or Child). Each profile belongs to a specific user, identified by userId.
 * 
 * @author Trudy Ann Roberts
 */
public class ProfileManager {
    private static final int MAX_PROFILES = 5; // Maximum number of profiles allowed per user
    private List<UserProfile> profiles = new ArrayList<>(); // To hold user profiles in memory
    private int userId; // The ID of the user (fetched from the database)

    /**
     * Constructs a ProfileManager for a specific user, identified by userId.
     * Upon initialization, the manager attempts to load any previously saved profiles for the user from a file.
     *
     * @param userId The ID of the user for whom this profile manager is created.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles from file upon initialization
    }

    /**
     * Adds a new profile for the logged-in user. If the maximum number of profiles 
     * is reached, a message is displayed and no profile is added.
     * 
     * @param profile UserProfile to be added.
     * @return true if the profile was successfully added, or false if the limit has been reached.
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
     * Saves the current list of profiles to a local file named profiles.txt.
     * Each profile is saved in CSV format: ProfileName,ProfileType,UserId.
     */
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
     * The file stores profiles in CSV format: ProfileName,ProfileType,UserId. 
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

    /**
     * Deletes a profile by name. If the profile is found and successfully deleted,
     * the profile file is updated accordingly.
     * 
     * @param profileName The name of the profile to be deleted.
     * @return true if the profile was successfully deleted, or false if the profile was not found.
     */
    public boolean deleteProfile(String profileName) {
        Optional<UserProfile> profileToRemove = profiles.stream()
                .filter(profile -> profile.getProfileName().equals(profileName))
                .findFirst();

        if (profileToRemove.isPresent()) {
            profiles.remove(profileToRemove.get());
            saveProfilesToFile(); // Update the file after deletion
            JOptionPane.showMessageDialog(null, "Profile '" + profileName + "' deleted.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Profile '" + profileName + "' not found.");
            return false;
        }
    }

    /**
     * Displays a GUI dialog allowing the user to select a profile from the list of available profiles.
     * If no profiles are available, a message is displayed to the user.
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
     * Saves the viewing history for a given profile. The viewing history is saved to a file named 
     * history_ProfileName.txt, where each profile has its own history file.
     * 
     * @param profile The profile for which the viewing history is being recorded.
     * @param filmTitle The title of the film watched.
     * @param filmId The ID of the film.
     * @param durationWatched The amount of time (in minutes) the user watched the film.
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
     * Retrieves the viewing history for a given profile from its associated history file.
     * The viewing history is stored in a file named history_ProfileName.txt.
     * 
     * @param profile The profile for which the viewing history is being retrieved.
     * @return A list of strings representing each viewing history entry, or an empty list if no history exists.
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
