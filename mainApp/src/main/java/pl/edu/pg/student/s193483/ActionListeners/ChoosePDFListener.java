package pl.edu.pg.student.s193483.ActionListeners;

import pl.edu.pg.student.s193483.PdfHandler;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class ChoosePDFListener
 * @brief Listener for the button for choosing a PDF
 */
public class ChoosePDFListener implements ActionListener {
    private final PdfHandler pdfHandler;    //!< Object working with a PDF, Signer of Verifier
    private JLabel status;                  //!< Reference to the status bar

    /**
     * @brief Default constructor
     * @param pdfHandler Class using a PDF
     * @param status    Status bar
     */
    public ChoosePDFListener(PdfHandler pdfHandler, JLabel status) {
        this.pdfHandler = pdfHandler;
        this.status = status;
    }

    /**
     * @brief Actions to be performed upon clicking the button for choosing a PDF
     * @details Opens a file chooser and sets the PDF file field in the object which will use it
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // open file chooser
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int openReturn = fileChooser.showOpenDialog(null);

        if (openReturn == JFileChooser.APPROVE_OPTION) {
            // provide the chosen PDF file
            pdfHandler.pdfFile = fileChooser.getSelectedFile();

            status.setText("PDF file chosen!");
        }
    }
}
