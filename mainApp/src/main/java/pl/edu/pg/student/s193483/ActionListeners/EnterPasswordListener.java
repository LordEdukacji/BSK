package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Signer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class EnterPasswordListener
 * @brief
 */
public class EnterPasswordListener implements ActionListener {

    private final JPasswordField passwordField; //!< PIN input field
    private final Signer signer;                //!< PDF signer
    private final JLabel status;                //!< Status bar
    private final CardLayout cardLayout;        //!< Card layout of the app, for changing cards
    private final JPanel cards;                 //!< Cards for the layout

    /**
     *
     * @param passwordField PIN input
     * @param signer PDF signer
     * @param status Status bar
     * @param cardLayout Card layout of the app
     * @param cards Cards for the layout
     */
    public EnterPasswordListener(JPasswordField passwordField, Signer signer, JLabel status, CardLayout cardLayout, JPanel cards) {
        this.passwordField = passwordField;
        this.signer = signer;
        this.status = status;
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    /**
     * @brief Actions to be performed upon clicking the button which confirms entering the password
     * @details Gets the password, passes it to the signer and signs the PDF
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            signer.setPassword(passwordField.getText());

            // signing
            String signedLocation = null;
            signedLocation = signer.signPDF();

            status.setText("Signed PDF " + signer.pdfFile.getName() + " to " + signedLocation);

            cardLayout.show(cards, "MAIN");
        } catch (Exception ex) {
            status.setText("Signing failed - " + ex.getMessage());

            // can try to sign again
            cardLayout.show(cards, "SIGN");
        }

    }
}
