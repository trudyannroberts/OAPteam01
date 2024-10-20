package userProfile;
import java.io.Serializable;

/**
 * Represents a user profile with a name and type (e.g., ADULT, CHILD).
 * Implements Serializable to support saving profiles as objects.
 * 
 * @author Trudy Ann Roberts
 */
public class UserProfile implements Serializable {
   

    public enum ProfileType {
        ADULT, CHILD
    }

    private String profileName;
    private ProfileType profileType;

    public UserProfile(String profileName, ProfileType profileType) {
        this.profileName = profileName;
        this.profileType = profileType;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    
    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }


    public String getProfileName() {
        return profileName;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public boolean canWatchRRated() {
        return profileType == ProfileType.ADULT;
    }
}
