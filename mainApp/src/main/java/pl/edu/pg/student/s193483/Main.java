package pl.edu.pg.student.s193483;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main app");
        JPanel mainPanel = new JPanel();

        JButton signButton = new JButton("Sign a PDF");
        JButton verifyButton = new JButton("Verify a signature");

        mainPanel.add(signButton);
        mainPanel.add(verifyButton);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}