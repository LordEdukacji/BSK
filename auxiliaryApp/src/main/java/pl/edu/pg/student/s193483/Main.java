package pl.edu.pg.student.s193483;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

        savePublicButton.addActionListener(new SaveButtonListener(generator, SaveButtonListener.SaveButtonType.SAVE_PUBLIC_KEY));
        savePrivateButton.addActionListener(new SaveButtonListener(generator, SaveButtonListener.SaveButtonType.SAVE_PRIVATE_KEY));

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