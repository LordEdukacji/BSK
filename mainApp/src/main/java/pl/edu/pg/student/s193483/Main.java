package pl.edu.pg.student.s193483;

import pl.edu.pg.student.s193483.ActionListeners.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Signer signer = new Signer();
        Verifier verifier = new Verifier();

        File[] drives;

        JFrame frame = new JFrame("Main app");
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        JPanel signPanel = new JPanel();
        JPanel verifyPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.add(mainPanel, "MAIN");
        cards.add(signPanel, "SIGN");
        cards.add(verifyPanel, "VERIFY");

        JButton goToSignButton = new JButton("Sign a PDF");
        JButton goToVerifyButton = new JButton("Verify a signature");

        goToSignButton.addActionListener(new GoToSignListener(cardLayout, cards));
        goToVerifyButton.addActionListener(new GoToVerifyListener(cardLayout, cards));

        // move functionality somewhere else later
        JButton choosePublicButton = new JButton("Choose file with public key");
        JButton choosePrivateButton = new JButton("Choose file with private key");
        JButton choosePDFButton = new JButton("(temporary) Choose PDF file");
        JButton choosePDFButton2 = new JButton("(temporary) Choose PDF file");
        JButton returnButton = new JButton("Return");
        JButton returnButton2 = new JButton("Return");

        JButton signButton = new JButton("Sign");
        JButton verifyButton = new JButton("Verify");

        choosePublicButton.addActionListener(new ChoosePublicKeyListener(verifier));
        choosePrivateButton.addActionListener(new ChoosePrivateKeyListener(signer));

        choosePDFButton.addActionListener(new ChoosePDFListener(verifier));
        choosePDFButton2.addActionListener(new ChoosePDFListener(signer));
        returnButton.addActionListener(new ReturnListener(cardLayout, cards));
        returnButton2.addActionListener(new ReturnListener(cardLayout, cards));

        signButton.addActionListener(new SignListener(signer));
        verifyButton.addActionListener(new VerifyListener(verifier));

        mainPanel.add(goToSignButton);
        mainPanel.add(goToVerifyButton);

        JLabel status = new JLabel("Status");
        JPanel statusPanel = new JPanel();
        statusPanel.add(status);
        
        verifyPanel.add(choosePublicButton);
        verifyPanel.add(choosePDFButton);
        verifyPanel.add(returnButton);
        verifyPanel.add(verifyButton);
        signPanel.add(choosePrivateButton);
        signPanel.add(choosePDFButton2);
        signPanel.add(returnButton2);
        signPanel.add(signButton);

        frame.add(cards, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}