package pl.edu.pg.student.s193483;

import java.io.File;

/**
 * @class PdfHandler
 * @brief Base class for the Signer and Verifier classes
 * @details Provides a field for a PDF file.
 */
public abstract class PdfHandler {
    public File pdfFile = null; //!< The PDF file to work with
}
