package pl.edu.pg.student.s193483;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Console;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(s);

        Generator generator = new Generator();
        try {
            generator.generateKeys(s);
            System.out.println(generator.publicKey);
            System.out.println(generator.privateKey);
            System.out.println(Arrays.toString(generator.encryptedPrivateKey));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}