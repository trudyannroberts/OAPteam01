package userProfile;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages user profiles, allowing saving and loading of profiles via serialization. 
 * It also handles profile selection, deletion, and managing viewing history for each profile.
 * 
 * This class supports storing up to five user profiles on a local file.
 * 
 * @author Trudy Ann Roberts
 */
public class ProfileManager implements Serializable {
   
    private static final int MAX_PROFILES = 5; // Maximum number of profiles allowed
    private List<UserProfile> profiles = new ArrayList<>(); // List to store user profiles
    private int userId; // ID of the user who owns the profiles

    /**
     * Constructs a ProfileManager for the given user. 
     * Loads any previously saved profiles from file.
     * 
     * @param userId The unique identifier for the user.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile();
    }

    /**
     * Adds a new profile to the profile list, provided the maximum number 
     * of profiles (5) has not been reached.
     * 
     * @param profile The profile to be added.
     * @return true if the profile was added successfully, false if the maximum number 
     *         of profiles has been reached.
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
     * Saves the current list of profiles to a file using serialization. 
     * Profiles are saved in a binary format to the file "profiles.dat".
     * 
     * @throws IOException If there is an error during the file writing process.
     */
    public void saveProfilesToFile() {
        try (FileOutputStream fout = new FileOutputStream("profiles.dat");
             ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(profiles);
            out.flush();
            out.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads profiles from the "profiles.dat" file using deserialization. 
     * If the file is not found, the profiles list remains empty.
     * 
     * @throws IOException If there is an error reading the file.
     * @throws ClassNotFoundException If the serialized class is not found.
     */
    @SuppressWarnings("unchecked") //To avoid "unchecked cast"-warning.
    private void loadProfilesFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("profiles.dat"))) {
            profiles = (List<UserProfile>) in.readObject(); // Deserialize the profiles list
            in.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Profile file not found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a profile by name. If the profile is found and successfully deleted, 
     * the profiles file is updated accordingly.
     * 
     * @param profileName The name of the profile to be deleted.
     * @return true if the profile was successfully deleted, false if the profile was not found.
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
     * Displays a dialog for the user to select a profile from the list of available profiles.
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
     * To be used in the ViewMyProfile class.
     * @return the list of profiles
     */
    public List<UserProfile> getProfiles() {
        return profiles; 
    }

    /**
     * Adds a record of a film watched by the user to the profile's viewing history.
     * The viewing history is saved in a file named "history_ProfileName.txt".
     * 
     * @param profile The profile for which the viewing history is being recorded.
     * @param filmTitle The title of the film watched.
     * @param filmId The ID of the film.
     * @param durationWatched The duration (in minutes) the user watched the film.
     * @throws IOException If there is an error while writing to the history file.
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
     * The viewing history is stored in a file named "history_ProfileName.txt".
     * 
     * @param profile The profile for which the viewing history is being retrieved.
     * @return A list of strings representing each viewing history entry, or an empty list if no history exists.
     * @throws IOException If there is an error while reading from the history file.
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
