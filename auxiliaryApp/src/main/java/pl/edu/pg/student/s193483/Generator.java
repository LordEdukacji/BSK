package pl.edu.pg.student.s193483;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * @class Generator
 * @brief Generates the RSA key pair.
 * @details The public key is available directly in its byte form, while the private key is secured using a PIN provided during the generation process.
 */
public class Generator {
    private PublicKey publicKey;        //!< The generated public key
    private byte[] iv;                  //!< The initialization vector used for encrypting the private key
    private byte[] salt;                //!< The salt used for encrypting the private key
    private byte[] encryptedPrivateKey; //!< The encrypted private key

    /**
     * @brief Provides the byte representation of the public key.
     * @return Byte array with the public key.
     */
    public byte[] getPublicKeyBytes() {
        return publicKey.getEncoded();
    }

    /**
     * @brief Provides the byte representation of the IV, salt and private key.
     * @return Byte array of the IV, salt and private key.
     */
    public byte[] getPrivateKeyBytes() {
        byte[] byteRepresentation = new byte[iv.length + salt.length + encryptedPrivateKey.length];
        ByteBuffer buffer = ByteBuffer.wrap(byteRepresentation);
        buffer.put(iv);
        buffer.put(salt);
        buffer.put(encryptedPrivateKey);
        byteRepresentation = buffer.array();

        return byteRepresentation;
    }

    /**
     * @brief Generates a pair of keys and saves them in the class fields
     * @details Uses the RSA algorithm with a keysize of 4096. The PIN is hashed with SHA256 and the private key encrypted with AES.
     * @param pin PIN used for securing the private key.
     */
    public void generateKeys(String pin) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();

        // generate a pair of RSA keys
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair rsaPair = keyPairGenerator.generateKeyPair();

        // save the pair of RSA keys
        publicKey = rsaPair.getPublic();
        PrivateKey privateKey = rsaPair.getPrivate();

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
