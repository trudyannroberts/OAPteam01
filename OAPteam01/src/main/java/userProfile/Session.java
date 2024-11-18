package userProfile;

/**
 * This class will store the user that is currently logged in.
 * 
 * @author Trudy Ann Roberts
 */

public class Session {
    private static User currentUser;

    /**
     * Sets the current user for the application session.
     * 
     * @param user the User object to set as the current user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }  
    
    /**
     * Retrieves the user ID of the currently logged-in user.
     * 
     * @return the user ID if a user is logged in, or -1 if no user is currently logged in
     */
    
    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1; 
    }
}
