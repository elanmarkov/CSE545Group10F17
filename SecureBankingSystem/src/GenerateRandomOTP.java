import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class GenerateRandomOTP {
	MessageDigest hashGenerator;
	Random randomGen;
	final int OTPSize = 8; // using 8 byte OTP
	public GenerateRandomOTP() {
		// Creates message digest and random seed generator
		try {
			hashGenerator = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		randomGen = new SecureRandom();
	}
	private byte[] otpGen() {
		// creates a new seed
	    byte[] newSeed = new byte[OTPSize];
	    randomGen.nextBytes(newSeed);
		return newSeed;
	}
	public String getNextOTP() {
		// Give the next OTP to be sent to the user
		// Created from hashed random seed
		byte[] newOTP = otpGen();
		StringBuffer hexVal = new StringBuffer();
		for (byte bytes : newOTP) {
			//convert hash to hex value
			hexVal.append(String.format("%02x", bytes & 0xff));
		}
		return hexVal.toString();
	}
}