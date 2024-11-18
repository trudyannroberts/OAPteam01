package userProfile;

import java.io.Serializable;

/**
 * Represents a user profile with a name and type (e.g., ADULT, CHILD).
 * Each profile is associated with a ProfileType, where we will have restrictions for the CHILD type.
 * This class implements Serializable to support saving profiles as serialized objects.
 * 
 * @author Trudy Ann Roberts
 */
@SuppressWarnings("serial")
public class UserProfile implements Serializable {
	
	/**
     * Enum representing the type of user profile, which can be either ADULT or CHILD.
     * This has not been implemented, but the idea is that in the future
     * ADULT profiles can watch R-rated films, while CHILD profiles cannot.
     */
    public enum ProfileType {
        ADULT, CHILD
    }

    private String profileName; // The name of the profile
    private ProfileType profileType; // The type of the profile (ADULT or CHILD)

    /**
     * Constructs a UserProfile with a given profile name and type.
     * 
     * @param profileName The name of the user profile.
     * @param profileType The type of the user profile (ADULT or CHILD).
     */
    public UserProfile(String profileName, ProfileType profileType) {
        this.profileName = profileName;
        this.profileType = profileType;
    }

    /**
     * Sets the name of the user profile.
     * 
     * @param profileName The new name to set for this profile.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * Sets the type of the user profile (ADULT or CHILD).
     * 
     * @param profileType The new type to set for this profile.
     */
    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    /**
     * Returns the name of the user profile.
     * 
     * @return The name of the profile.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Returns the type of the user profile (ADULT or CHILD).
     * 
     * @return The type of the profile.
     */
    public ProfileType getProfileType() {
        return profileType;
    }

    /**
     * Checks if the profile can watch R-rated films.
     * Only ADULT profiles are allowed to watch R-rated films.
     * 
     * @return true if the profile is an ADULT profile, false otherwise.
     */
    public boolean canWatchRRated() {
        return profileType == ProfileType.ADULT;
    }
}
