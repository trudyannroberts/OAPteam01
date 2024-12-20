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
	
	/**
	 * @param MAX_PROFILES refers to the maximum number of profiles per user
	 * @param profiles is an ArrayList that contains the list of user profiles
	 * @param userId is the userId (based off staff_id in the database) that will connect the user to their profiles
     */

    private static final int MAX_PROFILES = 5; 
    private List<UserProfile> profiles = new ArrayList<>(); 
    private int userId;

    /**
     * Constructs a ProfileManager for a specific user ID. 
     * Loads any saved profiles from the user's profile file.
     * */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfiles();
    }
    
    /**
     * Generates the file name for storing the user's profiles.
     * @return the name of the file.
     */
    private String getFileName() {
        return "profiles_" + userId + ".dat"; 
    }
    
    /**
     * Adds a new profile if the maximum limit has not been reached.
     * Saves the updated profile list to the file.
     */
    public boolean addProfile(UserProfile profile) {
        // Validate that the profile is not null and has valid fields
        if (profile == null || profile.getProfileName() == null || profile.getProfileName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Profile cannot be empty. Please provide valid details.");
            return false;
        }
        
        if (profiles.size() < MAX_PROFILES) {
            profiles.add(profile);
            saveProfiles();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Maximum number of profiles reached.");
            return false;
        }
    }
    
    /**
     * Saves the current list of profiles to the user's profile file via serialization.
     */
    public void saveProfiles() {
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
	public void loadProfiles() {
    	
        File profileFile = new File(getFileName());
        if (!profileFile.exists()) {
             if (userId != -1)
            // If the profile file does not exist, initialize with an empty list
            profiles = new ArrayList<>();
            return; // No need to try to load from the file
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(profileFile))) {
            profiles = (List<UserProfile>) in.readObject();
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
            saveProfiles();
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
        if (newProfileName == null || newProfileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "You must enter a new profile name.");
            return false;
        }
        Optional<UserProfile> profileToEdit = profiles.stream()
                .filter(profile -> profile.getProfileName().equals(oldProfileName))
                .findFirst();

        if (profileToEdit.isPresent()) {
            UserProfile profile = profileToEdit.get();
            profile.setProfileName(newProfileName);

            try {
                ProfileType type = ProfileType.valueOf(newProfileType.toUpperCase());
                profile.setProfileType(type);
                saveProfiles();
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
     * Prompts the user to select a profile from the available profiles.
     */
    public void promptUserToSelectProfile() {
        List<String> profileNames = profiles.stream()
                .map(UserProfile::getProfileName)
                .toList();

        if (!profileNames.isEmpty()) {
            String selectedProfile = (String) JOptionPane.showInputDialog(
                    null, "Select a profile:", "Profile Selection",
                    JOptionPane.QUESTION_MESSAGE, null, profileNames.toArray(), profileNames.get(0)
            );
            if (selectedProfile != null) {
                JOptionPane.showMessageDialog(null, "You selected profile: " + selectedProfile);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No profiles found for this user.");
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
