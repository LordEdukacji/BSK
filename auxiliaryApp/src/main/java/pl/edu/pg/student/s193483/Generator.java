package pl.edu.pg.student.s193483;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Generator {
    public PublicKey publicKey;
    public PrivateKey privateKey;
    public byte[] encryptedPrivateKey;
    public byte[] iv;
    public byte[] salt;

    public void generateKeys(String pin) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();

        // generate a pair of RSA keys
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);

        KeyPair rsaPair = keyPairGenerator.generateKeyPair();

        // save the pair of RSA keys
        publicKey = rsaPair.getPublic();
        privateKey = rsaPair.getPrivate();

        // generate the salt
        salt = new byte[16];
        secureRandom.nextBytes(salt);

        // hash the pin with SHA256
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(pin.toCharArray(), salt, 100000, 256);
        SecretKey secretKey = new SecretKeySpec(secretKeyFactory.generateSecret(pbeKeySpec).getEncoded(), "AES");

        // generate the IV
        iv = new byte[16];

        secureRandom.nextBytes(iv);

        // encrypt the private key with AES
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        encryptedPrivateKey = cipher.doFinal(privateKey.getEncoded());
    }
}
