package pl.edu.pg.student.s193483;

import pl.edu.pg.student.s193483.ActionListeners.*;

import javax.swing.*;
import java.awt.*;

/**
 * @class Main
 * @brief Main class
 * @details Handles the UI and initialization of functional elements
 */
public class Main {
    public static void main(String[] args) {
        // frame
        JFrame frame = new JFrame("Main app");
        frame.setLayout(new BorderLayout());

        // status labels
        JLabel status = new JLabel("Status");
        JPanel statusPanel = new JPanel();
        statusPanel.add(status);

        JLabel usbStatus = new JLabel("No USB");
        JPanel usbStatusPanel = new JPanel();
        usbStatusPanel.add(usbStatus);

        // panels
        JPanel mainPanel = new JPanel();
        JPanel signPanel = new JPanel();
        JPanel verifyPanel = new JPanel();
        JPanel passwordPanel = new JPanel();

        // cards
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
        cards.add(mainPanel, "MAIN");
        cards.add(signPanel, "SIGN");
        cards.add(verifyPanel, "VERIFY");
        cards.add(passwordPanel, "PIN");

        // buttons
        JButton goToSignButton = new JButton("Sign a PDF");
        JButton goToVerifyButton = new JButton("Verify a signature");

        // buttons
        JButton choosePublicButton = new JButton("Choose file with public key");
        JButton choosePDFButton = new JButton("Choose PDF file");
        JButton choosePDFButton2 = new JButton("Choose PDF file");
        JButton returnButton = new JButton("Return");
        JButton returnButton2 = new JButton("Return");
        JButton signButton = new JButton("Sign");
        JButton verifyButton = new JButton("Verify");
        JButton enterPasswordButton = new JButton("Enter");

        // password
        JLabel passwordLabel = new JLabel("Enter password:");
        JPasswordField passwordField = new JPasswordField(20);

        // USB handling
        UsbHolder usbHolder = new UsbHolder(usbStatus, status);
        UsbDetector usbDetector = new UsbDetector(usbHolder);
        Thread usbThread = new Thread(usbDetector);
        usbThread.start();

        // signer and verifier
        Signer signer = new Signer(usbHolder);
        Verifier verifier = new Verifier();

        // listeners for buttons
        goToSignButton.addActionListener(new GoToSignListener(cardLayout, cards));
        goToVerifyButton.addActionListener(new GoToVerifyListener(cardLayout, cards));

        choosePublicButton.addActionListener(new ChoosePublicKeyListener(verifier, status));

        choosePDFButton.addActionListener(new ChoosePDFListener(verifier, status));
        choosePDFButton2.addActionListener(new ChoosePDFListener(signer, status));
        returnButton.addActionListener(new ReturnListener(cardLayout, cards));
        returnButton2.addActionListener(new ReturnListener(cardLayout, cards));

        signButton.addActionListener(new SignListener(signer, status, cardLayout, cards));
        verifyButton.addActionListener(new VerifyListener(verifier, status));

        enterPasswordButton.addActionListener(new EnterPasswordListener(passwordField, signer, status, cardLayout, cards));

        // add components
        mainPanel.add(goToSignButton);
        mainPanel.add(goToVerifyButton);
        
        verifyPanel.add(choosePublicButton);
        verifyPanel.add(choosePDFButton);
        verifyPanel.add(returnButton);
        verifyPanel.add(verifyButton);
        signPanel.add(choosePDFButton2);
        signPanel.add(returnButton2);
        signPanel.add(signButton);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(enterPasswordButton);

        // layout
        frame.add(usbStatusPanel, BorderLayout.NORTH);
        frame.add(cards, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}