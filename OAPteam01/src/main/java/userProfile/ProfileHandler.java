package userProfile;

import java.util.List;

/**
 * Interface defining the operations for managing user profiles. Supports 
 * adding, editing, deleting, selecting, saving, and loading profiles, as well as 
 * retrieving the list of profiles associated with a specific user.
 * Classes implementing this interface can manage user profiles with these 
 * capabilities.
 * 
 * @author Trudy Ann Roberts
 */
public interface ProfileHandler {

    /**
     * Adds a new profile to the user's profile list, provided the maximum limit of 
     * profiles has not been reached.
     *
     * @param profile The UserProfile instance to be added.
     * @return true if the profile was successfully added; false if the maximum 
     *         number of profiles is already reached.
     */
    boolean addProfile(UserProfile profile);

    /**
     * Saves the current list of profiles to a file, persisting the user profile data
     * via serialization.
     */
    void saveProfiles();

    /**
     * Loads the list of profiles from the file into memory. If the file is not found,
     * initializes an empty profile list.
     */
    void loadProfiles();

    /**
     * Deletes a specific profile by its name from the user's profile list.
     *
     * @param profileName The name of the profile to delete.
     * @return true if the profile was successfully deleted; false if no profile with
     *         the specified name was found.
     */
    boolean deleteProfile(String profileName);

    /**
     * Edits an existing profile by updating its name and profile type.
     *
     * @param oldProfileName The current name of the profile to edit.
     * @param newProfileName The new name to assign to the profile.
     * @param newProfileType The new type to assign to the profile, which should be 
     *                       either "ADULT" or "CHILD".
     * @return true if the profile was successfully edited; false if the profile 
     *         was not found or if the new profile type was invalid.
     */
    boolean editProfile(String oldProfileName, String newProfileName, String newProfileType);

    /**
     * Loads the profiles associated with the user who is currently logged in.
     * Retrieves profiles from the appropriate file for the current user's ID.
     */
    void loadProfilesForCurrentUser();

    /**
     * Displays a selection dialog for choosing one of the available profiles.
     *
     * @return The name of the selected profile, or null if no profiles are available.
     */
    String selectProfile();

    /**
     * Retrieves the list of profiles associated with the user managed by this handler.
     *
     * @return A list of UserProfile objects representing the user's profiles.
     */
    List<UserProfile> getProfiles();
}
