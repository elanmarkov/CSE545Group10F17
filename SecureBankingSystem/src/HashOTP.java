import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class HashOTP {
	MessageDigest hashGenerator;
	Random seedGen;
	final int seedSize = 64; // using 64 bit seed
	public HashOTP() {
		try {
			hashGenerator = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: No such algorithm in Java MessageDigest");
			e.printStackTrace();
		}
		seedGen = new SecureRandom();
	}
	public byte[] configureOTP() {
	    byte[] newSeed = new byte[seedSize];
	    seedGen.nextBytes(newSeed);
		return newSeed;
	}
	public Boolean validateOTP(byte[] seed, byte[] OTP, int count) {
		byte[] correctVal = seed;
		for(int i = 1; i <= count; i++) {
			hashGenerator.update(correctVal); 
			correctVal = hashGenerator.digest();
		}
		hashGenerator.reset();
		return correctVal == OTP;
	}
	public byte[] getNextOTP(byte[] seed, int count) {
		byte[] newVal = seed;
		for(int i = 1; i <= count; i++) {
			hashGenerator.update(newVal); 
			newVal = hashGenerator.digest();
		}
		return newVal;
	}
}
