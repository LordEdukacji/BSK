package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.PdfHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChoosePDFListener implements ActionListener {
    private final PdfHandler pdfHandler;

    public ChoosePDFListener(PdfHandler pdfHandler) {
        this.pdfHandler = pdfHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int openReturn = fileChooser.showOpenDialog(null);

        if (openReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();

            pdfHandler.pdfFile = file;
        }
    }
}
