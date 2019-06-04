package file.pdf;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * （天威）电子签章读取
 */
public class SignatureReader {

    public static void main(String[] args) {

        try {
            getSignature();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public static void getSignature() throws IOException, GeneralSecurityException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        System.out.println("\n\ntest.pdf\n==============");

        //初始化一些莫名的东西，没搞懂干嘛用，反正得有，不然会报错
        //BouncyCastleProvider bcp = new BouncyCastleProvider();
        //Security.addProvider(bcp);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

//        try (   InputStream resource = getClass().getResourceAsStream("E:/files/test/test2.pdf") )
        try
        {
            //读取数据
            PdfReader reader = new PdfReader("E:/files/test/test.pdf");
            //
            AcroFields acroFields = reader.getAcroFields();
            //获取
            List<String> names = acroFields.getSignatureNames();
            for (String name : names) {
                System.out.println();
                System.out.println("Signature name: " + name);
                System.out.println(acroFields.verifySignature(name).getTimeStampToken());
                System.out.println("Signature covers whole document: " + acroFields.signatureCoversWholeDocument(name));
                PdfPKCS7 pk = acroFields.verifySignature(name);

                //签章信息
                X509Certificate x509Certificate = pk.getSigningCertificate();

                System.out.println(x509Certificate.getSigAlgName());
                System.out.println(x509Certificate.getSigAlgOID());
                System.out.println(x509Certificate.getSerialNumber());
                System.out.println(x509Certificate.getBasicConstraints());
                System.out.println(x509Certificate.getType());
                System.out.println();

                System.out.println("current date " + new Date());
                System.out.println("current date " + (new Date()).getTime());
                //有效期是日期
                System.out.println("before date " + x509Certificate.getNotBefore());
                System.out.println("before date " + x509Certificate.getNotBefore().getTime());
                //有效截止日期
                System.out.println("after date " + x509Certificate.getNotAfter());
                System.out.println("after date " + x509Certificate.getNotAfter().getTime());

                CertificateInfo.getSubjectFields(x509Certificate);

                //使用者
                System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
                System.out.println("Subject:SURNAME " + CertificateInfo.getSubjectFields(x509Certificate).getField("SURNAME"));
                System.out.println("Subject:CN " + CertificateInfo.getSubjectFields(x509Certificate).getField("CN"));
                System.out.println("Subject:OU " + CertificateInfo.getSubjectFields(x509Certificate).getField("OU"));
                System.out.println("Subject:O " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()).getField("O"));

                //发行者
                System.out.println("Issuer: " + CertificateInfo.getIssuerFields(x509Certificate));
                System.out.println("Issuer: " + CertificateInfo.getIssuerFields(x509Certificate).getField("SURNAME"));
                System.out.println("Issuer: " + CertificateInfo.getIssuerFields(x509Certificate).getField("CN"));
                System.out.println("Issuer: " + CertificateInfo.getIssuerFields(x509Certificate).getField("OU"));
                System.out.println("Issuer: " + CertificateInfo.getIssuerFields(x509Certificate).getField("O"));

                //是否有效
                System.out.println("Document verifies: " + pk.verify());

                Field rsaDataField = PdfPKCS7.class.getDeclaredField("RSAdata");
                rsaDataField.setAccessible(true);
                Object rsaDataFieldContent = rsaDataField.get(pk);
                if (rsaDataFieldContent != null && ((byte[])rsaDataFieldContent).length == 0)
                {
                    System.out.println("Found zero-length encapsulated content: ignoring");
                    rsaDataField.set(pk, null);
                }

                System.out.println("Document verifies: " + pk.verify());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();


        Field rsaDataField = PdfPKCS7.class.getDeclaredField("RSAdata");
        rsaDataField.setAccessible(true);
//        try (   InputStream resource = getClass().getResourceAsStream("E:/files/test/test2.pdf") )
        try
        {
            PdfReader reader = new PdfReader("E:/files/test/test.pdf");
            AcroFields acroFields = reader.getAcroFields();

            List<String> names = acroFields.getSignatureNames();
            for (String name : names) {
                System.out.println("Signature name: " + name);
                System.out.println("Signature covers whole document: " + acroFields.signatureCoversWholeDocument(name));
                PdfPKCS7 pk = acroFields.verifySignature(name);
                System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
                System.out.println("Subject: " + CertificateInfo.getIssuerFields(pk.getSigningCertificate()));

                System.out.println("Subject:SURNAME " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()).getField("SURNAME"));
                System.out.println("Subject:CN " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()).getField("CN"));
                System.out.println("Subject:OU " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()).getField("OU"));
                System.out.println("Subject:O " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()).getField("O"));

                Object rsaDataFieldContent = rsaDataField.get(pk);
                if (rsaDataFieldContent != null && ((byte[])rsaDataFieldContent).length == 0)
                {
                    System.out.println("Found zero-length encapsulated content: ignoring");
                    rsaDataField.set(pk, null);
                }
                System.out.println("Document verifies: " + pk.verify());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

