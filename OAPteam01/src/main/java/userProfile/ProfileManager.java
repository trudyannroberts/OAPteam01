package userProfile;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The ProfileManager class is responsible for managing user profiles, 
 * including saving and loading profiles via serialization, 
 * as well as handling profile selection, deletion, and managing viewing history.
 * This class supports storing up to five user profiles for a specific user ID on a local file.
 *
 * Profiles are saved to a file named "profiles_{userId}.dat", and each profile
 * is stored in a serialized format for persistence.
 * 
 * @author Trudy Ann Roberts
 */
public class ProfileManager implements Serializable {
   
    private static final int MAX_PROFILES = 5; // The maximum number of profiles a user can create
    private List<UserProfile> profiles = new ArrayList<>(); // Stores the user profiles in memory
    private int userId; // The unique ID of the user who owns the profiles

    /**
     * Constructs a ProfileManager for the specified user ID. 
     * Automatically attempts to load any saved profiles from the user's profile file.
     * If no profile file is found, an empty profile list is initialized.
     * 
     * @param userId The unique identifier for the user.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles specific to this user ID
        System.out.println("Loaded profiles for user ID: " + userId + ". Profiles count: " + profiles.size()); // Debug statement for verification
    }

    /**
     * Generates the file name for storing the user's profiles.
     * Each user has their own file based on their user ID.
     * 
     * @return The file name for storing the user's profiles.
     */
    private String getFileName() {
        return "profiles_" + userId + ".dat"; // Returns a unique file name based on user ID
    }

    /**
     * Adds a new profile to the user's profile list if the maximum number 
     * of profiles (5) has not been reached.
     * After adding the profile, it updates the file with the new list.
     *
     * @param profile The profile to be added.
     * @return true if the profile is successfully added, false if the maximum number is exceeded.
     */
    public boolean addProfile(UserProfile profile) {
        if (profiles.size() < MAX_PROFILES) {
            profiles.add(profile); // Adds the profile to the list
            saveProfilesToFile(); // Persist the updated list to file
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Maximum number of profiles reached."); // Alert user if limit is reached
            return false;
        }
    }

    /**
     * Saves the current list of profiles to the user's profile file using serialization.
     * If an error occurs during the save process, an error message is displayed to the user.
     */
    public void saveProfilesToFile() {
        try (FileOutputStream fout = new FileOutputStream(getFileName());
             ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(profiles); // Serializes the profiles list and saves it to the file
            out.flush(); // Ensures data is written to the file
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving profiles: " + e.getMessage()); // Displays error to user
            e.printStackTrace(); // Prints the stack trace for debugging purposes
        }
    }

    /**
     * Loads the list of profiles from the user's profile file using deserialization.
     * If the file does not exist, a new empty list is created. 
     * If an error occurs while loading, an error message is displayed.
     */
    @SuppressWarnings("unchecked")
    public void loadProfilesFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFileName()))) {
            profiles = (List<UserProfile>) in.readObject(); // Deserializes the profiles list from file
        } catch (FileNotFoundException e) {
            System.out.println("Profile file not found for user ID " + userId + ", starting fresh."); // No profiles yet for this user
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading profiles: " + e.getMessage()); // Displays error to user
            e.printStackTrace(); // Prints the stack trace for debugging purposes
        }
    }

    /**
     * Deletes a user profile based on the profile name. If the profile is successfully deleted,
     * the profiles file is updated to reflect the changes.
     * 
     * @param profileName The name of the profile to be deleted.
     * @return true if the profile was successfully deleted, false if the profile could not be found.
     */
    public boolean deleteProfile(String profileName) {
        Optional<UserProfile> profileToRemove = profiles.stream()
                .filter(profile -> profile.getProfileName().equals(profileName)) // Finds the profile with the specified name
                .findFirst();

        if (profileToRemove.isPresent()) {
            profiles.remove(profileToRemove.get()); // Removes the profile from the list
            saveProfilesToFile(); // Updates the file with the modified list
            JOptionPane.showMessageDialog(null, "Profile '" + profileName + "' deleted."); // Inform the user of successful deletion
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Profile '" + profileName + "' not found."); // Alert user if profile is not found
            return false;
        }
    }

    /**
     * Displays a dialog to the user to select a profile from the available profiles.
     * If no profiles are available, a message is displayed to the user.
     *
     * @return The name of the selected profile, or null if no profiles are available.
     */
    public String selectProfile() {
        if (profiles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No profiles available."); // Notify the user if no profiles exist
            return null;
        }

        String[] profileNames = profiles.stream()
                                        .map(UserProfile::getProfileName) // Collects profile names into an array
                                        .toArray(String[]::new);

        // Show a selection dialog to the user and return the selected profile name
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
     * Retrieves the list of profiles managed by this ProfileManager.
     * 
     * @return The list of profiles associated with the current user.
     */
    public List<UserProfile> getProfiles() {
        return profiles; 
    }

}
