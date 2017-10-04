import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/*
 * Secure Data Hashing Algorithm by Elan Markov
 * CSE 545 Group 10
 * This function will create a salted hash of the given input.
 * Also generates salts on request.
 * Salt stored together with password hash in database. */

public class DataHash {
	MessageDigest hasher;
	Random saltGen;
	final int saltSize = 32; // using 32 bit salt
	public DataHash() {
		try {
			hasher = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: No such algorithm in Java MessageDigest");
			e.printStackTrace();
		}
		saltGen = new SecureRandom();
	}
	public String hash(String password, String salt) {
		// hash the message with appended salt and return the hash value
		String hashVal = password + salt;
		StringBuffer hashBuffer = new StringBuffer();
		byte[] byteHashVal = hashVal.getBytes();
		hasher.update(byteHashVal);
		byte[] byteHash = hasher.digest(); // receive hash value
		for (byte bytes : byteHash) {
			//convert hash to hex value
			hashBuffer.append(String.format("%02x", bytes & 0xff));
		}
		hasher.reset();
		return hashBuffer.toString();
	}
	public String getNewSalt() {
		// Generate a (secure) random salt and return as a string.
	    byte[] salt = new byte[saltSize];
	    saltGen.nextBytes(salt);
		return new String(salt);
	}
}

/*
 * This will be used for unit tests later
DataHash hashMachine = new DataHash();
String salt = hashMachine.getNewSalt();
System.out.println("New salt: " + salt);
System.out.println(hashMachine.hash("I love america too much to leave it", salt));
System.out.println(hashMachine.hash("I don't love france", salt));*/
