package edu.duke.ece651.shared;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ProfessorAccount implements Account{
  private String userid;
  private String password;
  private String profid;
  
  private static final String ALGORITHM = "AES";
  private static final String KEY = "39edh*huis$iuh5yf@jf95jd";


  
  public ProfessorAccount(String userid, String password, String profid, boolean pwdIsEncrypted) {
    if(pwdIsEncrypted) {
      try {
        password = decrypt(password);
      } catch (Exception e) {
        System.out.println("Failed to create a professor account when decrypting the encrypted password: " + e.getMessage());
      }
    }
    if(password.equals("")){
      password = "password";
    }
    this.userid = userid;
    this.password = password;
    this.profid = profid;
  }

  public ProfessorAccount(String userid, String profid, boolean pwdIsEncrypted) {
    this(userid, "password", profid, pwdIsEncrypted);
  }

  public String getUserid() {
    return userid;
  }

  @Override
  public String getPersonalID() {
    return profid;
  }

  
  
  @Override
  public String getEncryptedPwd() {
    try {
      return encrypt(password);
    } catch (Exception e) {
      // e.printStackTrace();
      System.out.println("Failed to encrypt account password: " + e.getMessage());
      return null;
    }
  }
  
  @Override
  public String getAccountType() {
    return "professor";
  }

  @Override
  public boolean isCorrectPassword(String pwd) {
    if(pwd == null) {
      return false;
    }
    if(pwd.equals(password)) {
      return true;
    }
    return false;
  }

  public String encrypt(String strToEncrypt) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }
  
  public String decrypt(String strToDecrypt) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
    return new String(decryptedBytes);
  }
}
