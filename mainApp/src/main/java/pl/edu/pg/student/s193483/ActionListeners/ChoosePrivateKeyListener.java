package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.KeyExtractor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class ChoosePrivateKeyListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int openReturn = fileChooser.showOpenDialog(null);

        if (openReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();

            try (FileInputStream fileInputStream = new FileInputStream(fullPath)) {
                System.out.println("PASSWORD:");
                Scanner scanner = new Scanner(System.in);
                String password = scanner.nextLine();

                byte[] bytes = fileInputStream.readAllBytes();
                PrivateKey privateKey = KeyExtractor.extractPrivateKey(bytes, password);

                System.out.println(privateKey);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidKeySpecException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidAlgorithmParameterException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchPaddingException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalBlockSizeException ex) {
                throw new RuntimeException(ex);
            } catch (BadPaddingException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidKeyException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
