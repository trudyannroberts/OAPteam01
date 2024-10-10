package logic;

/**
 * Abstract class representing a user profile.
 * 
 * This class serves as a blueprint for specific profile types (e.g., adult or children's profiles) 
 * in the media streaming application. It provides basic functionality for managing a profile's name 
 * and defines a contract for determining if a profile can watch R-rated films.
 * 
 * @author Trudy Ann Roberts
 */
public abstract class UserProfile {
    
    // Encapsulated profile name
    private String profileName;
    
    /**
     * Constructor for UserProfile class.
     * 
     * @param profileName The name chosen for the profile. 
     *                    Must be non-null and non-empty.
     * @throws IllegalArgumentException if the profileName is null or empty.
     */
    public UserProfile(String profileName) {
        if (profileName == null || profileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile name cannot be null or empty.");
        }
        this.profileName = profileName;
    }

    /**
     * Getter for profile name.
     * 
     * @return The name associated with the profile.
     */
    public String getProfileName() {
        return profileName;
    }
  
    /**
     * Abstract method to check if the profile has access to R-rated films.
     * 
     * This method must be implemented by concrete subclasses (e.g., AdultProfile, ChildProfile),
     * to define the profile-specific rules regarding R-rated content.
     * 
     * @return true if the profile can watch R-rated films, false otherwise.
     */
    public abstract boolean canWatchRRated();
}
