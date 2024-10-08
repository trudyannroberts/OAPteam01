package logic;
import java.io.*;
import java.util.*;

public class ProfileManager {
    private static final int MAX_PROFILES = 5;
    private List<UserProfile> profiles = new ArrayList<>(); // To hold user profiles in memory
    private int userId; // The ID of the user (fetched from database)

    public ProfileManager(int userId) {
        this.userId = userId;
        loadProfilesFromFile(); // Load profiles from file upon initialization
    }

    // Add a new profile for the logged-in user
    public boolean addProfile(UserProfile profile) {
        if (profiles.size() < MAX_PROFILES) {
            profiles.add(profile);
            saveProfilesToFile(); // Save updated profiles to file
            return true;
        } else {
            System.out.println("Maximum number of profiles reached.");
            return false;
        }
    }

    // Save profiles to a local text file (linked to the user)
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

    // Load profiles from the file and filter by userId
    private void loadProfilesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("profiles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Assuming fields are comma-separated
                String profileName = parts[0];
                String profileType = parts[1];
                int storedUserId = Integer.parseInt(parts[2]);

                // Only load profiles for this user
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

    // Display the profiles in memory
    public void displayProfiles() {
        for (int i = 0; i < profiles.size(); i++) {
            UserProfile profile = profiles.get(i);
            System.out.println((i + 1) + ". Profile Name: " + profile.getProfileName() +
                    " | Can watch R-rated: " + profile.canWatchRRated());
        }
    }

    public int getProfileCount() {
        return profiles.size();
    }
}