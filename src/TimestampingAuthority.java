import javax.crypto.Cipher;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TimestampingAuthority {
	
	private static PublicKey pubKey;
	
    TimestampingAuthority() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
    {
    	InputStream ins = TimestampingAuthority.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "group10store".toCharArray());   //Keystore password
        java.security.cert.Certificate cert = keyStore.getCertificate("TSAkey");
        PublicKey publicKey = cert.getPublicKey();
        pubKey = publicKey;
    }

    public static PublicKey getPublicKey()
    {
    	return pubKey;
    }
    public static PrivateKey getPrivKeyFromStore() throws Exception {
        //Generated with:
    	// keytool -genkeypair -alias TSAkey -storepass group10store -keypass SSis#1 -keyalg RSA -keystore keystore.jks
    	// CN=TSA, OU=Group10, O=Software Security, L=Phoenix, ST=AZ, C=AZ
    	
        InputStream ins = TimestampingAuthority.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "group10store".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("SSis#1".toCharArray());    //Key password

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("TSAkey", keyPassword);
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return privateKey;
    }

    public static String encryptTimestamp(Timestamp time, PublicKey publicKey) throws Exception {
        Cipher encrypt = Cipher.getInstance("RSA");
        encrypt.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipher = encrypt.doFinal(time.toString().getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipher);
    }

    public static String decryptTimestamp(String cipherText) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        PrivateKey privKey = getPrivKeyFromStore();
        Cipher decript = Cipher.getInstance("RSA");
        decript.init(Cipher.DECRYPT_MODE, privKey);

        return new String(decript.doFinal(bytes), UTF_8);
    }

    public static String signTimestamp(Timestamp time) throws Exception {
    	PrivateKey privKey = getPrivKeyFromStore();
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privKey);
        privateSignature.update(time.toString().getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verifyTimestamp(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        String decyphered = Base64.getEncoder().encodeToString(signatureBytes);
        System.out.println("Sig: " + decyphered);

        return publicSignature.verify(signatureBytes);
    }

    public static void main(String[] args) throws Exception {
    	
    	Calendar calendar = Calendar.getInstance();
    	Date now = calendar.getTime();
    	Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
    	TimestampingAuthority TSA = new TimestampingAuthority();
    	
    	System.out.println(currentTimestamp.toString());

        //Encrypt
        String cipherText = encryptTimestamp(currentTimestamp, getPublicKey());
        System.out.println(cipherText);

        //Now decrypt
        String decipheredMessage = decryptTimestamp(cipherText);

        System.out.println(decipheredMessage);

        //Let's sign our message
        String signature = signTimestamp(currentTimestamp);
        System.out.println(signature);

        //Let's check the signature
        boolean isCorrect = verifyTimestamp(currentTimestamp.toString(), signature, getPublicKey());
        System.out.println("Signature correct: " + isCorrect);
    }
}
