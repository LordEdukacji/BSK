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
    private final Generator generator;  //!< Key pair generator
    private final SaveButtonType type;  //!< Which key to save - public or private
    private final JLabel status;        //!< Reference to the app's status bar

    /**
     * @enum SaveButtonType
     * @brief Represents which type of key this button saves
     */
    public enum SaveButtonType {
        SAVE_PRIVATE_KEY,   //!< Saves the private key
        SAVE_PUBLIC_KEY     //!< Saves the public key
    }

    /**
     * @brief default constructor
     * @param generator the key generator
     * @param type private key of public key
     * @param status reference to the label for displaying status information
     */
    public SaveButtonListener(Generator generator, SaveButtonType type, JLabel status) {
        this.generator = generator;
        this.type = type;
        this.status = status;
    }

    /**
     * @brief The sequence of actions to be carried upon clicking the save button
     * @details Opens a file chooser and saves the appropriate key to the correct location
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // create the file chooser
        JFileChooser fileChooser = new JFileChooser();

        // .key filter
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Key Files", "key");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // open the file chooser
        int saveReturn = fileChooser.showSaveDialog(null);

        if (saveReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();

            if (!fullPath.endsWith(".key")) {
                fullPath += ".key";
            }

            // overwrite warning
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
