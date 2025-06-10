package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Signer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterPasswordListener implements ActionListener {

    private final JPasswordField passwordField;
    private final Signer signer;
    private final JLabel status;
    private final CardLayout cardLayout;
    private final JPanel cards;

    public EnterPasswordListener(JPasswordField passwordField, Signer signer, JLabel status, CardLayout cardLayout, JPanel cards) {
        this.passwordField = passwordField;
        this.signer = signer;
        this.status = status;
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            signer.password = passwordField.getText();

            String signedLocation = null;
            signedLocation = signer.signPDF();

            status.setText("Signed PDF " + signer.pdfFile.getName() + " to " + signedLocation);

            cardLayout.show(cards, "MAIN");
        } catch (Exception ex) {
            status.setText("Signing failed - " + ex.getMessage());
            cardLayout.show(cards, "SIGN");
        }

    }
}
