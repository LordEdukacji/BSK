package pl.edu.pg.student.s193483;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Auxiliary app");
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Enter password:");
        JPasswordField textField = new JPasswordField(20);

        JButton generateButton = new JButton("Generate");
        JButton savePublicButton = new JButton("Save public key");
        JButton savePrivateButton = new JButton("Save private key");
        savePublicButton.setEnabled(false);
        savePrivateButton.setEnabled(false);
        JButton restartButton = new JButton("Restart");

        Generator generator = new Generator();

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                try {
                    generator.generateKeys(textField.getText());

                    savePublicButton.setEnabled(true);
                    savePrivateButton.setEnabled(true);
                    generateButton.setEnabled(false);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePublicButton.setEnabled(false);
                savePrivateButton.setEnabled(false);
                generateButton.setEnabled(true);
                textField.setText("");
            }
        });

        savePublicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileOutputStream fileOutputStream = new FileOutputStream("public.txt")) {
                    fileOutputStream.write(generator.getPublicKeyBytes());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        savePrivateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileOutputStream fileOutputStream = new FileOutputStream("private.txt")) {
                    fileOutputStream.write(generator.getPrivateKeyBytes());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(generateButton);
        panel.add(savePublicButton);
        panel.add(savePrivateButton);
        panel.add(restartButton);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}