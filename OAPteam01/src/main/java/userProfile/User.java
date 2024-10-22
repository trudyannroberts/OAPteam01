package userProfile;

/**
 * The User class represents a user in the media streaming service application.
 * It encapsulates the user's information (private parameters), including their personal details and credentials.
 * This class is used to create and manage user instances, primarily during the 
 * registration and authentication processes.
 * 
 * Each user has a first name, last name, email address, username, and password.
 * 
 * Note: The password here is not hashed, but will be hashed in the UserAuthenticator class.
 * 
 * @author Trudy Ann Roberts
 */
public class User {
    
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    /**
     * Constructs a User instance with the specified details.
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address
     * @param username the user's chosen username
     * @param password the user's password
     */
    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters
    /**
     * Returns the first name of the user.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email address of the user.
     * 
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the username of the user.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     * 
     * @return the password (considered sensitive information)
     */
    public String getPassword() {
        return password;
    }

    // Setters
    /**
     * Sets the first name of the user.
     * 
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the new password (should be hashed for security)
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
