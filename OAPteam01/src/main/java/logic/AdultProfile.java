package logic;

/**
 * Represents an adult user profile in the media streaming service.
 * 
 * This class extends the abstract class UserProfile and implements the 
 * logic for determining if the user has access to R-rated films. As this is an 
 * adult profile, access to R-rated films is always granted.
 * 
 * @author Trudy Ann Roberts
 */
public class AdultProfile extends UserProfile {

    /**
     * Constructor for the AdultProfile class.
     * 
     * This calls the constructor of the parent class UserProfile to set the profile name.
     * 
     * @param profileName The name chosen for this adult profile. 
     *                    It must not be null or empty, as validated by the parent class.
     */
    public AdultProfile(String profileName) {
        super(profileName);
    }

    /**
     * Determines if this profile has access to R-rated films.
     * 
     * Since this is an adult profile, it always returns {@code true}, allowing access to all films.
     * 
     * @return true, indicating that R-rated films can be viewed by this profile.
     */
    @Override
    public boolean canWatchRRated() {
        return true;
    }
}
