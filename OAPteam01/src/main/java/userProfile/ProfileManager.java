// userProfile/ProfileManager.java
package userProfile;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileManager implements ProfileHandler {

    private static final int MAX_PROFILES = 5;
    private List<UserProfile> profiles = new ArrayList<>();
    private int userId;

    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile();
    }

    private String getFileName() {
        return "profiles_" + userId + ".dat";
    }

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

    public void saveProfilesToFile() {
        try (FileOutputStream fout = new FileOutputStream(getFileName());
             ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(profiles);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void loadProfilesForCurrentUser() {
        int userId = Session.getCurrentUserId();  // Get the current user's ID from the session
        if (userId != -1) {
            loadProfilesFromFile(); // Assuming you pass userId to the file-loading method
        } else {
            JOptionPane.showMessageDialog(null, "No user is logged in.");
        }
    }

    @SuppressWarnings("unchecked")
	public void loadProfilesFromFile() {
        File profileFile = new File(getFileName());
        if (!profileFile.exists()) {
            profiles = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(profileFile))) {
            profiles = (List<UserProfile>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading profiles: " + e.getMessage() + "\nInitializing empty profile list.");
            profiles = new ArrayList<>();  // Initialize empty list if file is corrupted
            if (!profileFile.delete()) {  // Try deleting corrupted file
                JOptionPane.showMessageDialog(null, "Failed to delete corrupted profile file.");
            }
            e.printStackTrace();
        }
    }

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

    public boolean editProfile(String oldProfileName, String newProfileName, String newProfileType) {
        Optional<UserProfile> profileToEdit = profiles.stream()
                .filter(profile -> profile.getProfileName().equals(oldProfileName))
                .findFirst();

        if (profileToEdit.isPresent()) {
            UserProfile profile = profileToEdit.get();
            profile.setProfileName(newProfileName);

            try {
                UserProfile.ProfileType type = UserProfile.ProfileType.valueOf(newProfileType.toUpperCase());
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

    public List<UserProfile> getProfiles() {
        return profiles;
    }
}
