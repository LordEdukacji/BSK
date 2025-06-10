package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.Signer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignListener implements ActionListener {
    private final Signer signer;

    public SignListener(Signer signer) {
        this.signer = signer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        signer.signPDF();
    }
}
