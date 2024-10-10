package logic;

/**
 * Represents a children's user profile in the media streaming service.
 * 
 * This class extends the abstract class UserProfile and implements the logic 
 * for determining if the user has access to R-rated films. As this is a children's profile, 
 * access to R-rated films is always restricted.
 * 
 * @author Trudy Ann Roberts
 */
public class ChildrenProfile extends UserProfile {

    /**
     * Constructor for the ChildrenProfile class.
     * 
     * This calls the constructor of the parent class UserProfile to set the profile name.
     * 
     * @param profileName The name chosen for this children's profile. 
     *                    This must not be null or empty, as validated by the parent class.
     */
    public ChildrenProfile(String profileName) {
        super(profileName);
    }

    /**
     * Determines if this profile has access to R-rated films.
     * 
     * Since this is a children's profile, it always returns {@code false}, 
     * preventing access to films that are rated R.
     * 
     * @return false, indicating that R-rated films cannot be viewed by this profile.
     */
    @Override
    public boolean canWatchRRated() {
        return false; 
    }
}
