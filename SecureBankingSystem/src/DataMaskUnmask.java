import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class DataMaskUnmask {
	public DataMaskUnmask() {
		
	}
	// AES Encryption with OFB mode will be used for masking and unmasking.
	// Key managed elsewhere; this will only deal with encrypt/decrypt
	// Accepts byte array input; the caller will deal with casting.
	public byte[] mask(byte[] valToMask, byte[] key) {
		return null;
	}
	public byte[] unMask(byte[] valToUnmask, byte key[]) {
		return null;
	}
}
