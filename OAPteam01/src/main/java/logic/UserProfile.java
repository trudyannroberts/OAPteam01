package logic;
/**
 * @author Trudy Ann Roberts
 */

public abstract class UserProfile {
	
    protected String profileName;
    
    /**
     * @param profileName is the profileName the user has chosen.
     */
    public UserProfile(String profileName) {
        this.profileName = profileName;
    }
    /**
     * @return The profile name is returned.
     */

    public String getProfileName() {
        return profileName;
    }
  /**
   * 
   * A method that checks whether the profile has access to R-rated films or not.
   */
    public abstract boolean canWatchRRated();
}