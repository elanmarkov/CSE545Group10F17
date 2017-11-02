package com.group10.dbmodels;

import javax.crypto.Cipher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import org.apache.commons.codec.binary.Base64;
import java.sql.Timestamp;

import static java.nio.charset.StandardCharsets.UTF_8;

public class User {
	private int id;
	private String name;
	private String role;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String phone;
	private String dob;  // not used
	private String ssn;  // not used
	private String email;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getCountry() {
		return country;
		// TODO Auto-generated method stub

	}
	public void setCountry(String country) {
		this.country =  country;
		// TODO Auto-generated method stub

	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private PrivateKey getPrivKeyFromStore() throws Exception {
		if(this.role.compareTo("USER_ADMIN") == 0 ){
			//Generated with:
			// keytool -genkeypair -alias TSAkey -storepass group10store -keypass SSis#1 -keyalg RSA -keystore keystore.jks
			// CN=TSA, OU=Group10, O=Software Security, L=Phoenix, ST=AZ, C=AZ
			// We are aware that for optimal security this should not be here but for ease of use and testing we will assume this is secure.
	    	FileInputStream is = new FileInputStream("/home/CSE545Group10F17/BankingApp/src/main/java/com/group10/dbmodels/keystore.jks");

			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			keyStore.load(is, "group10store".toCharArray());   //Keystore password
			KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("SSis#1".toCharArray());    //Key password

			KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("TSAkey", keyPassword);
			PrivateKey privateKey = privateKeyEntry.getPrivateKey();

			return privateKey;
		}
		else {
			return null;
		}
	}

	public PublicKey getPublicKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		FileInputStream is = new FileInputStream("/home/CSE545Group10F17/BankingApp/src/main/java/com/group10/dbmodels/keystore.jks");
		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		java.security.cert.Certificate cert = keyStore.getCertificate("TSAkey");
		PublicKey publicKey = cert.getPublicKey();
		return publicKey;
	}

	public String encryptTimestamp(Timestamp time, PublicKey publicKey) throws Exception {
		Cipher encrypt = Cipher.getInstance("RSA");
		encrypt.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipher = encrypt.doFinal(time.toString().getBytes(UTF_8));

		return new String(Base64.encodeBase64(cipher));
	}

	public String decryptTimestamp(String cipherText) throws Exception {
		byte[] bytes = Base64.decodeBase64(cipherText);
		PrivateKey privKey = getPrivKeyFromStore();
		Cipher decript = Cipher.getInstance("RSA");
		decript.init(Cipher.DECRYPT_MODE, privKey);

		return new String(decript.doFinal(bytes), UTF_8);
	}

	public String signTimestamp(Timestamp time) throws Exception {
		PrivateKey privKey = getPrivKeyFromStore();
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(privKey);
		privateSignature.update(time.toString().getBytes(UTF_8));

		byte[] signature = privateSignature.sign();

		return new String(Base64.encodeBase64(signature));
	}

	public boolean verifyTimestamp(String plainText, String signature, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(plainText.getBytes(UTF_8));

		byte[] signatureBytes = Base64.decodeBase64(signature);
		String decyphered = new String(Base64.encodeBase64(signatureBytes));

		return publicSignature.verify(signatureBytes);
	}
	
	//only admin can see PII and sysLogs
	public String decryptPII(String plainText, PublicKey publicKey) throws Exception {
		Cipher encrypt = Cipher.getInstance("RSA");
		encrypt.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipher = encrypt.doFinal(plainText.getBytes(UTF_8));

		return new String(Base64.encodeBase64(cipher));
	}

	public String encryptPII(String cipherText) throws Exception {
		byte[] bytes = Base64.decodeBase64(cipherText);
		PrivateKey privKey = getPrivKeyFromStore();
		Cipher decript = Cipher.getInstance("RSA");
		decript.init(Cipher.DECRYPT_MODE, privKey);

		return new String(decript.doFinal(bytes), UTF_8);
	}
}
