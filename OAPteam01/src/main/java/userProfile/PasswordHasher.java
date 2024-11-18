package userProfile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException; //in case the system does not suppoert MD5.

/**
 * The PasswordHasher class provides functionality to hash plain-text passwords
 * using the MD5 hashing algorithm. This class is intended for securely storing 
 * user passwords in a database, ensuring that the actual passwords are not 
 * stored in plain text. The reason I chose MD5 is because it stores 32 characters, 
 * and the database has a max of 40 characters. I would prefer a stronger hashing method like BCrypt, 
 * but that stores 60 characters.

 * @author Trudy Ann Roberts
 */

public class PasswordHasher {
    /**
     * Hashes a plain-text password using the MD5 algorithm.
     * 
     * Converts the input password to a fixed-length 32-character 
     * hexadecimal string representation.
     * 
     * @param password the plain-text password to be hashed
     * @return the hashed password as a 32-character hexadecimal string
     * @throws RuntimeException if the MD5 algorithm is not supported by the system
     */
    public static String hashPassword(String password) {
        try {
            // Creating a MessageDigest object that implements the MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Converting the password into a byte array for hashing
            md.update(password.getBytes());
            
            // Hashes the password and produces the hashed password in bytes
            byte[] digest = md.digest();
            
            // Create a StringBuilder to construct the hexadecimal string
            StringBuilder sb = new StringBuilder();
            
            // Convert each byte to a two-digit hexadecimal string
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            
            // Return the hashed password as a string
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Throw a runtime exception if MD5 algorithm is not supported
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}
