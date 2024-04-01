package edu.duke.ece651.shared;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * represent the account of a professor.
 * can be imported from encrypted file,
 * password cannot be accessed outside of the object itself.
 */
public class ProfessorAccount implements Account{
  private String userid;
  private String password;
  private String profid;
  
  private static final String ALGORITHM = "AES";
  private static final String KEY = "39edh*huis$iuh5yf@jf95jd";


  /**
   * construct a professor account with userid, password, profid andd pwdIsEnceypted.
   * @param password can be original or encrypted
   * @param pwdIsEncrypted indicates whether the password is encrypted. 
   * True if it is, false if it is original.
   */
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

  /**
   * construct a professor account with userid, profid andd pwdIsEnceypted(without password defined).
   * Password will be set to be "password" by default.
   */
  public ProfessorAccount(String userid, String profid, boolean pwdIsEncrypted) {
    this(userid, "password", profid, pwdIsEncrypted);
  }

  /**
   * get user id of this account.
   */
  public String getUserid() {
    return userid;
  }

  /**
   * get personal id of the account user
   */
  @Override
  public String getPersonalID() {
    return profid;
  }

  /**
   * get the encrypted password
   */
  @Override
  public String getEncryptedPwd() throws Exception{
    return encrypt(password);
  }

  /**
   * get account type, always "professor" for this class.
   * @return "professor"
   */
  @Override
  public String getAccountType() {
    return "professor";
  }

  /**
   * check if the password is correct.
   */
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

  /**
   * encrypt a string.
   */
  private String encrypt(String strToEncrypt) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  /**
   * decrypt a string encrypted by the encrypt() method above
   */
  private String decrypt(String strToDecrypt) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
    return new String(decryptedBytes);
  }
}
