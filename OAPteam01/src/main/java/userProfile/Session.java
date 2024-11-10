package userProfile;

/**
 * This class will store the user that is currently logged in.
 * @author Trudy Ann Roberts
 */

public class Session {
    private static User currentUser;

    /**
     * This constructor sets a user as the current user of the application.
     * @param user is the user object
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    /**
     * @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * 
     * @return only the userId from the user object or -1 if no user is logged in.
     */
    
    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1;  // Return -1 if no user is logged in
    }
}
