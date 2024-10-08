package logic;
/**
 * @author Trudy Ann Roberts
 * This uses the AdultProfile() method from the abstract class UserProfile
 */

public class AdultProfile extends UserProfile {

    public AdultProfile(String profileName) {
        super(profileName);
    }
    /**
     * This returns true because this is an adult profile
     */
    @Override
    public boolean canWatchRRated() {
        return true;
    }
}
    