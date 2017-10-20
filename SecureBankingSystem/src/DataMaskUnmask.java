import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Random;

public class DataMaskUnmask {
	public DataMaskUnmask() {
		
	}
	// AES Encryption with CBC mode will be used for masking and unmasking.
	// Key managed elsewhere; this will only deal with encrypt/decrypt
	// Accepts byte array input; the caller will deal with casting.
	//public byte[] mask(byte[] valToMask, byte[] key) {
	    public static String encrypt(String key1, String key2, String value) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

	            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"),
	                    "AES");
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	            byte[] encrypted = cipher.doFinal(value.getBytes());
	            return encrypted;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return null;
	    }
		return null;
	//}
	public byte[] unMask(byte[] valToUnmask, byte key[]) {
		 try {
	            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

	            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"),
	                    "AES");
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	            byte[] original = cipher.doFinal(valToUnmask);

	            return original;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return null;
		return null;
	}
}
