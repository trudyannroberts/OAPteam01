package userProfile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Since BCrypt automatically uses 60 characters when storing a password, and the database has a max of 40,
 * I had to create this class that changes the automatic storage of password to 40 characters.
 * author Trudy Ann Roberts
 */

public class PasswordHasher {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            // Truncate to 40 characters
            return hexString.toString().substring(0, 40);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}