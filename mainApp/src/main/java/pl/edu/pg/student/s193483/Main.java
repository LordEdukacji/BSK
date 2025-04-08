package pl.edu.pg.student.s193483;

import pl.edu.pg.student.s193483.ActionListeners.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main app");
        JPanel mainPanel = new JPanel();
        JPanel signPanel = new JPanel();
        JPanel verifyPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.add(mainPanel, "MAIN");
        cards.add(signPanel, "SIGN");
        cards.add(verifyPanel, "VERIFY");

        JButton signButton = new JButton("Sign a PDF");
        JButton verifyButton = new JButton("Verify a signature");

        signButton.addActionListener(new SignListener(cardLayout, cards));
        verifyButton.addActionListener(new VerifyListener(cardLayout, cards));

        // move functionality somewhere else later
        JButton choosePublicButton = new JButton("(temporary) Choose file with public key");
        JButton choosePrivateButton = new JButton("(temporary) Choose file with private key");
        JButton choosePDFButton = new JButton("(temporary) Choose PDF file");
        JButton choosePDFButton2 = new JButton("(temporary) Choose PDF file");
        JButton returnButton = new JButton("Return");
        JButton returnButton2 = new JButton("Return");

        choosePublicButton.addActionListener(new ChoosePublicKeyListener());
        choosePrivateButton.addActionListener(new ChoosePrivateKeyListener());

        choosePDFButton.addActionListener(new ChoosePDFListener());
        choosePDFButton2.addActionListener(new ChoosePDFListener());
        returnButton.addActionListener(new ReturnListener(cardLayout, cards));
        returnButton2.addActionListener(new ReturnListener(cardLayout, cards));

        mainPanel.add(signButton);
        mainPanel.add(verifyButton);

        // remove later
        verifyPanel.add(choosePublicButton);
        verifyPanel.add(choosePDFButton);
        verifyPanel.add(returnButton);
        signPanel.add(choosePrivateButton);
        signPanel.add(choosePDFButton2);
        signPanel.add(returnButton2);

        frame.add(cards);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}