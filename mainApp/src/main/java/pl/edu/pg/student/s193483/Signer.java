package pl.edu.pg.student.s193483;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.concurrent.CancellationException;

public class Signer extends PdfHandler {
    public final UsbHolder usbHolder;
    public String password;

    public Signer(UsbHolder usbHolder) {
        super();
        this.usbHolder = usbHolder;
    }

    public String signPDF() throws Exception {
        try {
            if (pdfFile == null) throw new IllegalArgumentException("PDF file required!");

            PrivateKey privateKey = KeyExtractor.extractPrivateKey(usbHolder.privateKeyBytes, password);
            if (privateKey == null) throw new IllegalArgumentException("Private key required!");

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PDF Files", "pdf");
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            int saveReturn = fileChooser.showSaveDialog(null);

            if (saveReturn == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String fullPath = file.getAbsolutePath();

                if (!fullPath.endsWith(".pdf")) {
                    fullPath += ".pdf";
                }

                if (file.exists()) {
                    int confirmation = JOptionPane.showConfirmDialog(null,
                            "This file already exists. Do you want to overwrite it?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmation != JOptionPane.OK_OPTION) {
                        throw new CancellationException("Signing process cancelled");
                    }
                }

                PdfReader pdfReader = new PdfReader(pdfFile.getAbsolutePath());

                PrivateKeySignature privateKeySignature = new PrivateKeySignature(privateKey, "SHA256", null);

                FileOutputStream outputStream = new FileOutputStream(fullPath);
                PdfStamper stamper = PdfStamper.createSignature(pdfReader, outputStream, '\0');

                PdfSignatureAppearance signatureAppearance = stamper.getSignatureAppearance();

                RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) privateKey;
                RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(), rsaPrivateCrtKey.getPublicExponent());
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(pubSpec);

                JcaX509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                        new X500Name("CN=issuer"),
                        BigInteger.ZERO,
                        new Date(System.currentTimeMillis()),
                        new Date(System.currentTimeMillis() + 1000L * 24 * 60 * 60 * 1000), // 1000 days
                        new X500Name("CN=subject"),
                        publicKey
                );
                X509CertificateHolder certHolder = certGen.build(new JcaContentSignerBuilder("SHA256WithRSA").build(privateKey));

                X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(certHolder);

                ExternalDigest digest = new BouncyCastleDigest();

                MakeSignature.signDetached(signatureAppearance, digest, privateKeySignature, new Certificate[] {certificate}, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

                stamper.close();

                return fullPath;
            }

            throw new Exception("Something else went wrong");

        } catch (IOException | DocumentException | GeneralSecurityException | OperatorCreationException e) {
            throw new Exception(e);
        }
    }
}
