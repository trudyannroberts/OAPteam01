package logic;

import logic.UserProfile.ProfileType;

/**
 * @author Trudy Ann Roberts
 */

public class UserProfile {
	
	public enum ProfileType{
		ADULT,
		CHILD
	}
	
    private String profileName;
    private ProfileType profileType;
    
    /**
     * @param profileName is the profileName the user has chosen.
     */
    public UserProfile(String profileName, ProfileType profileType) {
        this.profileName = profileName;
        this.profileType = profileType;
    }
    
    public void setProfileName(String profileName) {
    	this.profileName = profileName;
    }
    /**
     * @return The profile name is returned.
     */

    public String getProfileName() {
        return profileName;
    }
    
    public ProfileType getProfileType() {
    	return profileType;
    }
  /**
   * 
   * A method that checks whether the profile has access to R-rated films or not.
   */
    public boolean canWatchRRated() {
    	return profileType == ProfileType.ADULT;
    }
   

}