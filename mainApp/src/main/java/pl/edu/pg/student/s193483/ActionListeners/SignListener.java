package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Signer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignListener implements ActionListener {
    private final Signer signer;
    private final JLabel status;
    private final CardLayout cardLayout;
    private final JPanel cards;

    public SignListener(Signer signer, JLabel status, CardLayout cardLayout, JPanel cards) {
        this.signer = signer;
        this.status = status;
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (signer.pdfFile != null && signer.usbHolder.privateKeyBytes != null) {
                cardLayout.show(cards, "PIN");
            }
            else {
                status.setText("Choose a PDF file and insert a USB drive with a private key");
            }
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }
}
