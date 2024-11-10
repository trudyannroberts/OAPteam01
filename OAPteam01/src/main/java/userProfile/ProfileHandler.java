package userProfile;


import java.util.List;

/**
 * Interface defining the operations for managing user profiles.
 * Allows adding, editing, deleting, selecting, saving, loading profiles, 
 * and retrieving the list of profiles associated with a user.
 * @author Trudy Ann Roberts 
 */
public interface ProfileHandler {

    /**
     * Adds a new profile if the maximum number of profiles has not been reached.
     *
     * @param profile The profile to add.
     * @return true if the profile was added, false if the maximum number is reached.
     */
    boolean addProfile(UserProfile profile);

    /**
     * Saves the current list of profiles to file.
     */
    void saveProfiles();

    /**
     * Loads the profiles from file into memory.
     */
    void loadProfiles();

    /**
     * Deletes a profile by name.
     *
     * @param profileName The name of the profile to delete.
     * @return true if the profile was deleted, false if not found.
     */
    boolean deleteProfile(String profileName);

    /**
     * Edits an existing profile's name and type.
     *
     * @param oldProfileName The name of the profile to edit.
     * @param newProfileName The new name to assign to the profile.
     * @param newProfileType The new type to assign to the profile ("ADULT" or "CHILD").
     * @return true if the profile was successfully edited, false if not found or invalid type.
     */
    boolean editProfile(String oldProfileName, String newProfileName, String newProfileType);

    /**
     * Displays a dialog to select a profile from the available profiles.
     *
     * @return The name of the selected profile, or null if no profiles are available.
     */
    String selectProfile();
    
   void loadProfilesForCurrentUser();

    /**
     * Retrieves the list of profiles.
     *
     * @return The list of profiles associated with the user.
     */
    List<UserProfile> getProfiles();
}
