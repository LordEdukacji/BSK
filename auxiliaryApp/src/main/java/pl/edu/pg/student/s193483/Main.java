package pl.edu.pg.student.s193483;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class Main
 * @brief Main class
 * @details Handles the UI and initialization of functional elements
 */
public class Main {
    public static void main(String[] args) {
        // window
        JFrame frame = new JFrame("Auxiliary app");
        frame.setLayout(new BorderLayout());

        // panels
        JPanel panel = new JPanel();
        JPanel statusPanel = new JPanel();

        // labels and password field
        JLabel label = new JLabel("Enter password:");
        JPasswordField textField = new JPasswordField(20);
        JLabel status = new JLabel("Status");

        // buttons
        JButton generateButton = new JButton("Generate");
        JButton savePublicButton = new JButton("Save public key");
        JButton savePrivateButton = new JButton("Save private key");
        savePublicButton.setEnabled(false);
        savePrivateButton.setEnabled(false);
        JButton restartButton = new JButton("Restart");



        // initialize the generator
        Generator generator = new Generator();

        // generate keys
        generateButton.addActionListener(a -> {
            try {
                var password = textField.getText();
                if (password.isEmpty()) throw new IllegalArgumentException("Please provide a password");

                generator.generateKeys(password);

                savePublicButton.setEnabled(true);
                savePrivateButton.setEnabled(true);
                generateButton.setEnabled(false);

                status.setText("Keys generated!");
            } catch (Exception e) {
                status.setText(e.getMessage());
            }
        });

        // resets the form
        restartButton.addActionListener(e -> {
            savePublicButton.setEnabled(false);
            savePrivateButton.setEnabled(false);
            generateButton.setEnabled(true);

            textField.setText("");
            status.setText("Generator reset");
        });

        // add listeners
        savePublicButton.addActionListener(new SaveButtonListener(generator, SaveButtonListener.SaveButtonType.SAVE_PUBLIC_KEY, status));
        savePrivateButton.addActionListener(new SaveButtonListener(generator, SaveButtonListener.SaveButtonType.SAVE_PRIVATE_KEY, status));

        // add UI elements
        panel.add(label);
        panel.add(textField);
        panel.add(generateButton);
        panel.add(savePublicButton);
        panel.add(savePrivateButton);
        panel.add(restartButton);
        statusPanel.add(status);

        // layout
        frame.add(panel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}