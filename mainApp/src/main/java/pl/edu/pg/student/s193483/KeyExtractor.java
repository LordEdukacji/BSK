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

public class KeyExtractor {
    public static PublicKey extractPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        EncodedKeySpec spec = new X509EncodedKeySpec(bytes);

        return factory.generatePublic(spec);
    }

    public static PrivateKey extractPrivateKey(byte[] bytes, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] iv = new byte[16];
        buffer.get(iv, 0, 16);

        byte[] salt = new byte[16];
        buffer.get(salt, 0, 16);

        buffer.position(32);
        byte[] encryptedKey = new byte[buffer.remaining()];
        buffer.get(encryptedKey);

        SecretKey secretKey = hashPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] privateKey = cipher.doFinal(encryptedKey);

        KeyFactory factory = KeyFactory.getInstance("RSA");
        EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);

        return factory.generatePrivate(spec);
    }

    private static SecretKey hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 100000, 256);

        return new SecretKeySpec(secretKeyFactory.generateSecret(pbeKeySpec).getEncoded(), "AES");
    }
}
