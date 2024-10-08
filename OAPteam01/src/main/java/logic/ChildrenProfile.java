package logic;
/**
 * @author Trudy Ann Roberts
 */
public class ChildrenProfile extends UserProfile {
	/**
	 * @param profileName is the profileName the user has chosen for the profile
	 */
    public ChildrenProfile(String profileName) {
        super(profileName);
    }
    /**
     * @return This returns false because a child cannot watch an R-rated film
     */
    @Override
    public boolean canWatchRRated() {
        return false; 
    }
}