package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Verifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class VerifyListener
 * @brief Listener for the verify button
 */
public class VerifyListener implements ActionListener {
    private final Verifier verifier;    //!< Signature verifier
    private final JLabel status;        //!< Status bar

    /**
     * @brief Default constructor
     * @param verifier Signature verifier
     * @param status Status bar
     */
    public VerifyListener(Verifier verifier, JLabel status) {
        this.verifier = verifier;
        this.status = status;
    }

    /**
     * @brief Actions to be performed upon clicking the verify button
     * @details Prompts the verifier to verify the signature and sets the status appropriately
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // waiting status
            status.setText("Verifying...");

            // verification
            verifier.verifySignature();

            // success
            status.setText(verifier.pdfFile.getName() + " verified successfully!");
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }
}
