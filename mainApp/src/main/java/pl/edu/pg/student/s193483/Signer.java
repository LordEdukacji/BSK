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
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
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

public class Signer extends PdfHandler {
    public PrivateKey privateKey;

    public void signPDF() {
        try {
            PdfReader pdfReader = new PdfReader(pdfFile.getAbsolutePath());

            PrivateKeySignature privateKeySignature = new PrivateKeySignature(privateKey, "SHA256", null);

            FileOutputStream outputStream = new FileOutputStream("signed.pdf");
            PdfStamper stamper = PdfStamper.createSignature(pdfReader, outputStream, '\0');

            PdfSignatureAppearance signatureAppearance = stamper.getSignatureAppearance();

            RSAPrivateCrtKey privk = (RSAPrivateCrtKey) privateKey;
            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(pubSpec);

            JcaX509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                    new X500Name("CN=issuer"),
                    BigInteger.ZERO,
                    new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis() + 1000L * 24 * 60 * 60 * 1000),
                    new X500Name("CN=subject"),
                    publicKey
            );
            X509CertificateHolder certHolder = certGen.build(new JcaContentSignerBuilder("SHA256WithRSA").build(privateKey));

            X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(certHolder);

            ExternalDigest digest = new BouncyCastleDigest();

            MakeSignature.signDetached(signatureAppearance, digest, privateKeySignature, new Certificate[] {certificate}, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

            stamper.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (OperatorCreationException e) {
            throw new RuntimeException(e);
        }
    }
}
