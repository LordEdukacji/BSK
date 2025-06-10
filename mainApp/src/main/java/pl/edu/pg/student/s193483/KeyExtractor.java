package pl.edu.pg.student.s193483;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @class KeyExtractor
 * @brief Converts the keys in their byte form into appropriate objects.
 * @details Functions are exposed as static. Returns PrivateKey or PublicKey objects from java.security.
 */
public class KeyExtractor {
    /**
     * @brief Extract the RSA public key from the byte array
     * @param bytes Public key bytes
     * @return Public key object
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey extractPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        EncodedKeySpec spec = new X509EncodedKeySpec(bytes);

        return factory.generatePublic(spec);
    }

    /**
     * @brief Extract the encrypted RSA private key using a password.
     * @param bytes Private key bytes
     * @param password PIN used to encrypt the private key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static PrivateKey extractPrivateKey(byte[] bytes, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // IV
        byte[] iv = new byte[16];
        buffer.get(iv, 0, 16);

        // salt
        byte[] salt = new byte[16];
        buffer.get(salt, 0, 16);

        // private key
        buffer.position(32);
        byte[] encryptedKey = new byte[buffer.remaining()];
        buffer.get(encryptedKey);

        // hashing
        SecretKey secretKey = hashPassword(password, salt);

        // decrypt the key
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] privateKey = cipher.doFinal(encryptedKey);

        // obtain the private key object
        KeyFactory factory = KeyFactory.getInstance("RSA");
        EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);

        return factory.generatePrivate(spec);
    }

    /**
     * @brief Hash the PIN to obtain the password used in the encryption process
     * @param password The PIN used to encrypt the password
     * @param salt The salt used during the encryption process, to be read from the file in which the encrypted key is saved.
     * @return Secret key for decrypting the private key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKey hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 100000, 256);

        return new SecretKeySpec(secretKeyFactory.generateSecret(pbeKeySpec).getEncoded(), "AES");
    }
}
