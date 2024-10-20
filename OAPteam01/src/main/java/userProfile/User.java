package userProfile;


public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password; // Store only the hashed password

    // Constructor for registration
    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    // Getters
    public String getFirstName() { 
    	return firstName; 
    }
    public String getLastName() { 
    	return lastName; 
    }
    public String getEmail() { 
    	return email; 
    }
    public String getUsername() { 
    	return username; 
    }
    public String getPassword() { 
    	return password;
    }
}

