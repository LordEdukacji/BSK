package pl.edu.pg.student.s193483;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

public class SaveButtonListener implements ActionListener {
    private Generator generator;
    private SaveButtonType type;

    public enum SaveButtonType {
        SAVE_PRIVATE_KEY, SAVE_PUBLIC_KEY
    }

    public SaveButtonListener(Generator generator, SaveButtonType type) {
        this.generator = generator;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int saveReturn = fileChooser.showSaveDialog(null);

        if (saveReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();

            if (!fullPath.endsWith(".txt")) {
                fullPath += ".txt";
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
                } else if (this.type == SaveButtonType.SAVE_PRIVATE_KEY) {
                    fileOutputStream.write(generator.getPrivateKeyBytes());
                } else {
                    throw new IllegalArgumentException("Unknown button type");
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }
}
