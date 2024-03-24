package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class FileEncryptorDecryptorTest {
  private static final String workingDir = System.getProperty("user.dir");
  private static final String DATA_PATH = workingDir + "/encryptfile/";

  @Test
  void testEncryptDecrypt() throws Exception {

    String inputFile = DATA_PATH + "test.txt";
    String encryptedFile = DATA_PATH + "encryptedTest.txt";
    String decryptedFile = DATA_PATH + "decryptedTest.txt";

    FileEncryptorDecryptor.encrypt(inputFile, encryptedFile);
    File encrypted = new File(encryptedFile);
    assertTrue(encrypted.exists());

    FileEncryptorDecryptor.decrypt(encryptedFile, decryptedFile);
    File decrypted = new File(decryptedFile);
    assertTrue(decrypted.exists());

    byte[] originalContent = Files.readAllBytes(Paths.get(inputFile));
    byte[] encryptedContent = Files.readAllBytes(Paths.get(encryptedFile));
    assertNotEquals(new String(originalContent), new String(encryptedContent));

    byte[] decryptedContent = Files.readAllBytes(Paths.get(decryptedFile));
    assertArrayEquals(originalContent, decryptedContent);

  }

}
