import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataHash {
	static MessageDigest hasher;
	public DataHash() {
		try {
			hasher = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: No such algorithm in Java MessageDigest");
			e.printStackTrace();
		}
	}
	public static String hash(String password, byte[] salt) {
		// hash the message with appended salt and return the hash value
		String hashVal = "";
		return hashVal;
	}
	public byte[] getNewSalt() {
		// Make a new salt to be stored for the user.
		return null;
	}
}
