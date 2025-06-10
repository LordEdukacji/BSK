package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.KeyExtractor;
import pl.edu.pg.student.s193483.Verifier;

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
    private final Verifier verifier;
    private final JLabel status;

    public ChoosePublicKeyListener(Verifier verifier, JLabel status) {
        this.verifier = verifier;
        this.status = status;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Key Files", "key");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int openReturn = fileChooser.showOpenDialog(null);

        if (openReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();

            try (FileInputStream fileInputStream = new FileInputStream(fullPath)) {
                byte[] bytes = fileInputStream.readAllBytes();

                verifier.publicKey = KeyExtractor.extractPublicKey(bytes);

                status.setText("Public key chosen!");
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                status.setText("Not a valid public key file!");
            }
        }
    }
}
