package userProfile;

/**
 * The User class represents a user in the media streaming service. 
 * It stores basic user information such as first name, last name, email, 
 * username, and password. 
 * 
 * The class provides getter and setter methods to access and modify 
 * the user's information. While the password is stored in plain text for 
 * simplicity, it is recommended to store a hashed version of the password 
 * in a real-world application for security reasons.
 * 
 * TODO: Hash passwords
 * 
 * @author Trudy Ann Roberts
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password; // You could also store a hashed version of the password

    /**
     * Constructs a new {@code User} object with the provided details.
     * 
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param email     the user's email address
     * @param username  the user's chosen username
     * @param password  the user's password (in practice, use a hashed version)
     */
    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the user's first name.
     * 
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     * 
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the user's email address.
     * 
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's username.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     *
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's first name.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the user's email address.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the  User object. The string 
     * contains the user's first name, last name, email, and username.
     * 
     * The password is intentionally excluded from the string representation.
     * 
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
