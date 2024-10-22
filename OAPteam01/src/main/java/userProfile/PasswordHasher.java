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
	 * 
	 * @param password is the plain-text password from the user
	 * @return the hashed password (converted to a String)
	 */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); //Creating a MessageDigest object that implements the MD5 algorithm.
            md.update(password.getBytes()); //converting the password into a byte array (as MessageDigest does read binary data, not text)
            byte[] digest = md.digest(); //hashes the password and produces the hashed password in bytes.
            StringBuilder sb = new StringBuilder(); // creating a StringBuilder object.
            for (byte b : digest) { //iterate over every byte in the digest array
                sb.append(String.format("%02x", b)); //each byte (b) is converted to a two-digit hexadecimal string and appended into the StringBuilder.
            }
            return sb.toString(); //returns the StringBuilder (converted into java string)
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throwing a RuntimeException if the MD5 algorithm is not supported
        }
    }
}

