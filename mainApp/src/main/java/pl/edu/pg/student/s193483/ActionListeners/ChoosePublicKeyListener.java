package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.KeyExtractor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class ChoosePublicKeyListener implements ActionListener {
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
                byte[] bytes = fileInputStream.readAllBytes();
                PublicKey publicKey = KeyExtractor.extractPublicKey(bytes);

                System.out.println(publicKey);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidKeySpecException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
