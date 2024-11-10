package userProfile;

public class Session {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1;  // Return -1 if no user is logged in
    }

    public static void clearUser() {
        currentUser = null;
    }
}
