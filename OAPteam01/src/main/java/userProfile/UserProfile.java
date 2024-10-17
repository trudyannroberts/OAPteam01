package userProfile;
import java.io.Serializable;

import userProfile.UserProfile.ProfileType;

/**
 * Represents a user profile in the media streaming service, which can either be an adult or child profile.
 * A profile determines access to certain content based on the profile type.
 * 
 * @author Trudy Ann Roberts
 */
public class UserProfile implements Serializable {
    
    /**
     * Enum representing the type of profile. Can either be an ADULT or a CHILD profile.
     */
    public enum ProfileType {
        ADULT, // Allows access to R-rated films
        CHILD  // Restricts access to R-rated films
    }
    
    private String profileName;  // Name of the profile chosen by the user
    private ProfileType profileType;  // Type of profile (Adult or Child)

    /**
     * Constructs a UserProfile object with a specified profile name and type.
     * 
     * @param profileName The name of the profile chosen by the user.
     * @param profileType The type of profile, either ADULT or CHILD.
     */
    public UserProfile(String profileName, ProfileType profileType) {
        this.profileName = profileName;
        this.profileType = profileType;
    }

    /**
     * Sets the profile name for this user profile.
     * 
     * @param profileName The new name of the profile.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    
    /**
     * Sets the profile type for this user profile.
     * @param profileType The type of profile (either ADULT or CHILD).
     */
    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    /**
     * Retrieves the name of this profile.
     * 
     * @return The profile name.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Retrieves the type of this profile (either ADULT or CHILD).
     * 
     * @return The type of profile.
     */
    public ProfileType getProfileType() {
        return profileType;
    }

    /**
     * Determines whether the profile has access to R-rated films. Only adult profiles
     * are allowed to watch R-rated content.
     * 
     * @return {@code true} if the profile is of type ADULT and can watch R-rated films; 
     *         {@code false} if the profile is a CHILD.
     */
    public boolean canWatchRRated() {
        return profileType == ProfileType.ADULT;
    }
}
