package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Verifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifyListener implements ActionListener {
    private final Verifier verifier;

    public VerifyListener(Verifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        verifier.verifySignature();
    }
}
