package edu.duke.ece651.shared;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Provides functionality to encrypt and decrypt files using the AES algorithm.
 */
public class FileEncryptorDecryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final String KEY = "hj7x89H$yuBI0456";

    /**
     * Encrypts a file with AES encryption using a predefined key.
     *
     * @param inputFile  the path to the file to be encrypted.
     * @param outputFile the path where the encrypted data should be written.
     * @throws Exception if an error occurs during encryption.
     */
    public static void encrypt(String inputFile, String outputFile) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, KEY, inputFile, outputFile);
    }

    /**
     * Decrypts an AES-encrypted file using a predefined key.
     *
     * @param inputFile  the path to the encrypted file to be decrypted.
     * @param outputFile the path where the decrypted data should be written.
     * @throws Exception if an error occurs during decryption.
     */
    public static void decrypt(String inputFile, String outputFile) throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, KEY, inputFile, outputFile);
    }

    /**
     * Performs encryption or decryption on a file.
     *
     * @param cipherMode the operation mode.
     * @param key        the encryption key to be used.
     * @param inputFile  the path to the input file.
     * @param outputFile the path for the output file.
     * @throws Exception if an error occurs during the crypto operation.
     */
    private static void doCrypto(int cipherMode, String key, String inputFile, String outputFile) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new java.io.File(inputFile).length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
}
