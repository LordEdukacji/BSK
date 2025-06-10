package pl.edu.pg.student.s193483;


import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import java.io.Console;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Arrays;

public class Verifier extends PdfHandler {
    public PublicKey publicKey;

    public void verifySignature() {
        try {
            PdfReader pdfReader = new PdfReader(pdfFile.getAbsolutePath());
            AcroFields acroFields = pdfReader.getAcroFields();

            for (String name : acroFields.getSignatureNames()) {
                PdfPKCS7 pdfPKCS7 = acroFields.verifySignature(name);
                System.out.println(pdfPKCS7);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
