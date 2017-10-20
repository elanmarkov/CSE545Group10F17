import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TimestampingAuthority {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public static KeyPair getKeyPairFromKeyStore() throws Exception {
        //Generated with:
    	// keytool -genkeypair -alias TSAkey -storepass group10store -keypass SSis#1 -keyalg RSA -keystore keystore.jks
    	// CN=TSA, OU=Group10, O=Software Security, L=Phoenix, ST=AZ, C=AZ
    	
        InputStream ins = TimestampingAuthority.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "group10store".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("SSis#1".toCharArray());    //Key password

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("TSAkey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("TSAkey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }

    public static String encryptTimestamp(Timestamp time, PublicKey publicKey) throws Exception {
        Cipher encrypt = Cipher.getInstance("RSA");
        encrypt.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipher = encrypt.doFinal(time.toString().getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipher);
    }

    public static String decryptTimestamp(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decript = Cipher.getInstance("RSA");
        decript.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decript.doFinal(bytes), UTF_8);
    }

    public static String signTimestamp(Timestamp time, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
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
    	
    	System.out.println(currentTimestamp.toString());
    	
        //First generate a public/private key pair
        //KeyPair pair = generateKeyPair();
        KeyPair pair = getKeyPairFromKeyStore();

        //Encrypt
        String cipherText = encryptTimestamp(currentTimestamp, pair.getPublic());
        System.out.println(cipherText);

        //Now decrypt
        String decipheredMessage = decryptTimestamp(cipherText, pair.getPrivate());

        System.out.println(decipheredMessage);

        //Let's sign our message
        String signature = signTimestamp(currentTimestamp, pair.getPrivate());
        System.out.println(signature);

        //Let's check the signature
        boolean isCorrect = verifyTimestamp(currentTimestamp.toString(), signature, pair.getPublic());
        System.out.println("Signature correct: " + isCorrect);
    }
}