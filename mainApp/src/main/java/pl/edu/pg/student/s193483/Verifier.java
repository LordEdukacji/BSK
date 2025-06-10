package pl.edu.pg.student.s193483;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.*;

/**
 * @class Verifier
 * @brief Verifies PDF signatures
 */
public class Verifier extends PdfHandler {
    public PublicKey publicKey;     //!< Public key for verification

    /**
     * @brief Verify the signature on the PDF. Throws an exception when unsuccessful
     * @throws Exception Reason for failing the verification
     */
    public void verifySignature() throws Exception {
        try {
            if (publicKey == null) throw new IllegalArgumentException("Public key required!");
            if (pdfFile == null) throw new IllegalArgumentException("PDF file required!");

            // load the file
            PdfReader pdfReader = new PdfReader(pdfFile.getAbsolutePath());

            // get fields
            AcroFields acroFields = pdfReader.getAcroFields();

            // BouncyCastle issue fix
            Security.addProvider(new BouncyCastleProvider());

            // no signatures
            if (acroFields.getSignatureNames().isEmpty()) throw new Exception("This PDF is unsigned!");

            // runs for all signatures
            // would throw an error if there is at least one signature different from the one we are checking for
            for (String name : acroFields.getSignatureNames()) {
                PdfPKCS7 pdfPKCS7 = acroFields.verifySignature(name);

                // does nothing when successful, throws an exception when fails
                pdfPKCS7.getSigningCertificate().verify(publicKey);

                // checks for modifications in the pdf
                if (!acroFields.signatureCoversWholeDocument(name)) throw new Exception("This PDF was modified!");
            }
        } catch (Exception e) {
            throw new Exception(pdfFile.getName() + " verification failed - " + e.getMessage());
        }
    }
}
