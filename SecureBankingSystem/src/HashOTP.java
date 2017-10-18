/*
 * Hash-based OTP by Elan Markov
 * CSE 545 Group 10
 * Creates a random 64-bit seed and creates a hash-chained OTP.
 * Caller must store seed and current count value.
 * Current hash value counts down from a caller-chosen value (e.g. 100, 99, ..., 1)
 * New password sent to user after last password is entered.
 * */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class HashOTP {
	MessageDigest hashGenerator;
	Random seedGen;
	final int seedSize = 64; // using 64 bit seed
	public HashOTP() {
		// Creates message digest and random seed generator
		try {
			hashGenerator = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: No such algorithm in Java MessageDigest");
			e.printStackTrace();
		}
		seedGen = new SecureRandom();
	}
	public byte[] configureOTP() {
		// creates a new seed
	    byte[] newSeed = new byte[seedSize];
	    seedGen.nextBytes(newSeed);
		return newSeed;
	}
	public Boolean validateOTP(byte[] seed, byte[] OTP, int count) {
		// checks if current OTP value is equal to current expected OTP value
		// based on the count for the OTP
		byte[] correctVal = seed;
		for(int i = 1; i <= count; i++) {
			hashGenerator.update(correctVal); 
			correctVal = hashGenerator.digest();
		}
		hashGenerator.reset();
		return correctVal == OTP;
	}
	public byte[] getNextOTP(byte[] seed, int count) {
		// Give the next OTP to be sent to the user
		byte[] newVal = seed;
		for(int i = 1; i <= count; i++) {
			hashGenerator.update(newVal); 
			newVal = hashGenerator.digest();
		}
		return newVal;
	}
}
