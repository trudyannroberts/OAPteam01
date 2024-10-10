package logic;
import java.io.*;
import java.util.*;

/**
 * @author Trudy Ann Roberts
 * The ProfileManager class lets a user create up to five profiles, either an adult or children's profile.
 * The data is stored in a local file, and each profile is linked to a specific user via a userId.
 */
public class ProfileManager {

    private static final int MAX_PROFILES = 5;
    private List<UserProfile> profiles = new ArrayList<>(); // To hold user profiles in memory
    private int userId; // The ID of the user (fetched from database)

    /**
     * Initializes the ProfileManager for a specific user, using their userId.
     * Profiles linked to this user are loaded from the file.
     *
     * @param userId The userId of the user that is logged in. This is the staff_id from the database.
     */
    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles from file upon initialization
    }

    /**
     * Adds a new profile for the logged-in user. Users can create up to five profiles.
     * If the profile is successfully added, it is saved to the local file.
     *
     * @param profile The new profile that the user creates (either Adult or Children).
     * @return true if the profile was successfully added and saved to the file, 
     *         false if the maximum number of profiles has already been reached.
     */
    public boolean addProfile(UserProfile profile) {
        if (profiles.size() < MAX_PROFILES) {
            profiles.add(profile);
            saveProfilesToFile(); // Save the updated list to the file
            return true;
        } else {
            System.out.println("Maximum number of profiles reached.");
            return false;
        }
    }

    /**
     * Saves the user's profiles to a local text file. 
     * Each profile is associated with the userId of the current user, and duplicates are avoided.
     */
    private void saveProfilesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("profiles.txt", true))) {
            for (UserProfile profile : profiles) {
                String profileType = profile instanceof AdultProfile ? "Adult" : "Children";
                String line = profile.getProfileName() + "," + profileType + "," + userId;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
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
            System.out.println("Profile file not found, starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays all the profiles currently loaded in memory for this user.
     * It shows the profile index, profile name, and whether the profile can watch R-rated movies.
     */
    public void displayProfiles() {
        for (int i = 0; i < profiles.size(); i++) {
            UserProfile profile = profiles.get(i);
            System.out.println((i + 1) + ". Profile Name: " + profile.getProfileName() +
                    " | Can watch R-rated: " + profile.canWatchRRated());
        }
    }

    /**
     * Returns the number of profiles that the user has created.
     *
     * @return The count of profiles currently associated with the userId.
     */
    public int getProfileCount() {
        return profiles.size();
    }
}
