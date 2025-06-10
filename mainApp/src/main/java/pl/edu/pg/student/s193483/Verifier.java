package pl.edu.pg.student.s193483;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.*;

public class Verifier extends PdfHandler {
    public PublicKey publicKey;

    public void verifySignature() throws Exception {
        try {
            if (publicKey == null) throw new IllegalArgumentException("Public key required!");
            if (pdfFile == null) throw new IllegalArgumentException("PDF file required!");

            PdfReader pdfReader = new PdfReader(pdfFile.getAbsolutePath());
            AcroFields acroFields = pdfReader.getAcroFields();

            // BouncyCastle issue fix
            Security.addProvider(new BouncyCastleProvider());

            if (acroFields.getSignatureNames().isEmpty()) throw new Exception("This PDF is unsigned!");

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
