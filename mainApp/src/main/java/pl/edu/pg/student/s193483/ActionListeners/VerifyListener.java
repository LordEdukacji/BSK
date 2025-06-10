package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Verifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifyListener implements ActionListener {
    private final Verifier verifier;
    private final JLabel status;

    public VerifyListener(Verifier verifier, JLabel status) {
        this.verifier = verifier;
        this.status = status;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            status.setText("Verifying...");

            verifier.verifySignature();

            status.setText(verifier.pdfFile.getName() + " verified successfully!");
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }
}
