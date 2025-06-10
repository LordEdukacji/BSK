package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Signer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class SignListener
 * @brief Listener for the sign button
 */
public class SignListener implements ActionListener {
    private final Signer signer;            //!< Signature creator
    private final JLabel status;            //!< Status bar
    private final CardLayout cardLayout;    //!< Card layout of the app, for changing cards
    private final JPanel cards;             //!< Cards for the layout

    /**
     * @brief Default constructor
     * @param signer Signature creator
     * @param status Status bar
     * @param cardLayout Card layout of the app
     * @param cards Cards for the layout
     */
    public SignListener(Signer signer, JLabel status, CardLayout cardLayout, JPanel cards) {
        this.signer = signer;
        this.status = status;
        this.cardLayout = cardLayout;
        this.cards = cards;
    }

    /**
     * @brief Actions to be performed upon clicking the sign button
     * @details Goes to the password screen if possible, displays a warning otherwise
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // has everything necessary for signing
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
