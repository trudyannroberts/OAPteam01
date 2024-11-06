package userProfile;

import javax.swing.*;
import userProfile.UserProfile.ProfileType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The ProfileManager class manages user profiles, including saving and loading profiles 
 * via serialization, as well as handling profile selection, deletion, and viewing history.
 * Supports storing up to five user profiles for a specific user ID in a local file.
 * Profiles are saved to a file named "profiles_{userId}.dat" in serialized format.
 * 
 * @author Trudy Ann Roberts
 */
public class ProfileManager implements ProfileHandler {

    private static final int MAX_PROFILES = 5; // Maximum number of profiles per user
    private List<UserProfile> profiles = new ArrayList<>(); // In-memory list of user profiles
    private int userId; // Unique ID of the user owning the profiles

    /**
     * Constructs a ProfileManager for a specific user ID. 
     * Loads any saved profiles from the user's profile file.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile();
    }

    /**
     * Generates the file name for storing the user's profiles.
     */
    private String getFileName() {
        return "profiles_" + userId + ".dat"; 
    }

    /**
     * Adds a new profile if the maximum limit has not been reached.
     * Saves the updated profile list to the file.
     */
    public boolean addProfile(UserProfile profile) {
        if (profiles.size() < MAX_PROFILES) {
            profiles.add(profile);
            saveProfilesToFile();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Maximum number of profiles reached.");
            return false;
        }
    }

    /**
     * Saves the current list of profiles to the user's profile file via serialization.
     */
    public void saveProfilesToFile() {
        try (FileOutputStream fout = new FileOutputStream(getFileName());
             ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(profiles);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads profiles from the user's profile file. Initializes an empty list if the file is not found.
     */
    @SuppressWarnings("unchecked")
    public void loadProfilesFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFileName()))) {
            profiles = (List<UserProfile>) in.readObject();
        } catch (FileNotFoundException e) {
        	JOptionPane.showMessageDialog(null,"Profile file not found for user ID");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a profile by name, if it exists, and updates the profiles file.
     */
    public boolean deleteProfile(String profileName) {
        Optional<UserProfile> profileToRemove = profiles.stream()
                .filter(profile -> profile.getProfileName().equals(profileName))
                .findFirst();

        if (profileToRemove.isPresent()) {
            profiles.remove(profileToRemove.get());
            saveProfilesToFile();
            JOptionPane.showMessageDialog(null, "Profile '" + profileName + "' deleted.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Profile '" + profileName + "' not found.");
            return false;
        }
    }

    /**
     * Edits an existing profile by updating its name and type, then saves changes to file.
     */
    public boolean editProfile(String oldProfileName, String newProfileName, String newProfileType) {
        Optional<UserProfile> profileToEdit = profiles.stream()
                .filter(profile -> profile.getProfileName().equals(oldProfileName))
                .findFirst();

        if (profileToEdit.isPresent()) {
            UserProfile profile = profileToEdit.get();
            profile.setProfileName(newProfileName);

            try {
                ProfileType type = ProfileType.valueOf(newProfileType.toUpperCase());
                profile.setProfileType(type);
                saveProfilesToFile();
                JOptionPane.showMessageDialog(null, "Profile '" + oldProfileName + "' updated to '" + newProfileName + "'.");
                return true;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Invalid profile type. Use 'ADULT' or 'CHILD'.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Profile '" + oldProfileName + "' not found.");
            return false;
        }
    }

    /**
     * Displays a dialog to the user for selecting a profile from available profiles.
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
     * Retrieves the list of profiles managed by this ProfileManager.
     */
    public List<UserProfile> getProfiles() {
        return profiles; 
    }
}
