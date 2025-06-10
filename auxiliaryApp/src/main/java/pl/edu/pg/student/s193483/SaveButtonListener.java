package pl.edu.pg.student.s193483;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @class SaveButtonListener
 * @brief Handles saving a key upon clicking the save button
 */
public class SaveButtonListener implements ActionListener {
    private final Generator generator;  /** Key pair generator */
    private final SaveButtonType type;  /** Which key to save - public or private */
    private final JLabel status;        /** Reference to the app's status bar */

    public enum SaveButtonType {
        SAVE_PRIVATE_KEY, SAVE_PUBLIC_KEY
    }

    public SaveButtonListener(Generator generator, SaveButtonType type, JLabel status) {
        this.generator = generator;
        this.type = type;
        this.status = status;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Key Files", "key");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int saveReturn = fileChooser.showSaveDialog(null);

        if (saveReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();

            if (!fullPath.endsWith(".key")) {
                fullPath += ".key";
            }

            if (file.exists()) {
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "This file already exists. Do you want to overwrite it?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation != JOptionPane.OK_OPTION) {
                    return;
                }
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(fullPath)) {
                if (this.type == SaveButtonType.SAVE_PUBLIC_KEY) {
                    fileOutputStream.write(generator.getPublicKeyBytes());
                    status.setText("Public key saved!");
                } else if (this.type == SaveButtonType.SAVE_PRIVATE_KEY) {
                    fileOutputStream.write(generator.getPrivateKeyBytes());
                    status.setText("Private key saved!");
                } else {
                    throw new IllegalArgumentException("Unknown button type");
                }

            } catch (Exception ex) {
                status.setText(ex.getMessage());
            }
        }
    }
}
