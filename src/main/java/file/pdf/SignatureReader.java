package file.pdf;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.io.source.RASInputStream;
import com.itextpdf.io.source.RandomAccessFileOrArray;
import com.itextpdf.io.source.RandomAccessSourceFactory;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.signatures.SignatureUtil;
import com.spire.pdf.security.PdfSignature;
import com.spire.pdf.widget.PdfFormFieldWidgetCollection;
import com.spire.pdf.widget.PdfFormWidget;
import com.spire.pdf.widget.PdfSignatureFieldWidget;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * （天威）电子签章读取
 */
public class SignatureReader {

    public static void main(String[] args) {
//        getSignature();

        getSignature2();

//        getSignature3();
    }

//    private static void getSignature(){
//        //加载含有签名的PDF文件
//        PdfReader pdfReader = new PdfReader("E:/files/test/test2.pdf");
//
//        PdfDocument pdfDocument = new PdfDocument(pdfReader);
//
//        //获取域集合
//        PdfFormWidget pdfFormWidget = (PdfFormWidget) pdfDocument.getForm();
//        PdfFormFieldWidgetCollection pdfFormFieldWidgetCollection = pdfFormWidget.getFieldsWidget();
//
//        //获取签名域
//        for (int i = 0; i < pdfFormFieldWidgetCollection.getCount(); i++) {
//            if (pdfFormFieldWidgetCollection.get(i) instanceof PdfSignatureFieldWidget) {
//                PdfSignatureFieldWidget signatureFieldWidget = (PdfSignatureFieldWidget) pdfFormFieldWidgetCollection.get(i);
//
//                //获取签名
//                PdfSignature signature = signatureFieldWidget.getSignature();
//
//                //判断签名是否有效
//                boolean result = signature.verifySignature();
//                if(result) {
//                    System.out.println("有效签名");
//                }else
//                {
//                    System.out.println("无效签名");
//                }
//            }
//        }
//    }

    private static void getSignature2(){
        boolean result = false;

        try {

            PdfReader pdfReader = new PdfReader("E:/files/test/test2.pdf");

            PdfDocument pdfDocument = new PdfDocument(pdfReader);
            SignatureUtil signatureUtil = new SignatureUtil(pdfDocument);
            List<String> signedNames = signatureUtil.getSignatureNames();

            //遍历签名的内容并做验签
            for (String signedName : signedNames) {

                //获取签名值
                byte[] signedData = getSignData(signatureUtil , signedName);

                //校验签名
//                result = SignUtil.verifyP7AttachData(signedData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return result;
    }

    /**
     * 获取签名数据
     * @param signatureUtil
     * @param signedName
     * @return
     */ private static byte[] getSignData(SignatureUtil signatureUtil, String signedName) {
         PdfDictionary pdfDictionary = signatureUtil.getSignatureDictionary(signedName);
         PdfString contents = pdfDictionary.getAsString(PdfName.Contents);
         return contents.getValueBytes();
     }
     /**
     * 获取源数据（如果subFilter使用的是Adbe.pkcs7.detached就需要在验签的时候获取 源数据 并与 签名数据 进行 p7detach 校验）
     * @param pdfReader
     * @param signatureUtil
     * @param signedName
     * @return
     */
//     private static byte[] getOriginData(PdfReader pdfReader, SignatureUtil signatureUtil, String signedName) {
//         byte[] originData = null;
//         try {
//             PdfSignature pdfSignature = signatureUtil.getSignature(signedName);
//             PdfArray pdfArray = pdfSignature.getByteRange();
//             RandomAccessFileOrArray randomAccessFileOrArray = pdfReader.getSafeFile();
//             InputStream rg = new RASInputStream(new RandomAccessSourceFactory().createRanged(randomAccessFileOrArray.createSourceView(), SignatureUtil.asLongArray(pdfArray)));
//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//             byte[] buf = new byte[8192]; int n = 0;
//             while (-1 != (n = rg.read(buf))) {
//                 outputStream.write(buf, 0, n);
//             }
//             originData = outputStream.toByteArray();
//         } catch (IOException e) {
//             e.printStackTrace();
//         } return originData;
//     }



    public static void getSignature3(){
//        try {
//            PdfReader reader = new PdfReader("E:/files/test/test.pdf");
//            AcroFields acroFields = reader.getAcroFields();
//            if (acroFields == null) {
//                return ;
//            }
//
//            List<String> signatureNames = acroFields.getSignatureNames();
//            if (signatureNames == null || signatureNames.size() == 0) {
//                return ;
//            }
//
//            reader.close();
//            for (String str : signatureNames) {
//                PdfDictionary sigDict = acroFields.getSignatureDictionary(str);
//                if (sigDict == null) {
//                    continue;
//                }
//
//
//                PdfName sub = sigDict.getAsName(PdfName.SUBFILTER);
//                if (PdfName.ADBE_PKCS7_DETACHED.equals(sub)) {
//                    System.out.println("subCode ADBE_PKCS7_DETACHED");
//                }
////                if (PdfName.ADBE_X509_RSA_SHA1.equals(sub)) {
////签章对应的证书
//                    PdfString certStr = sigDict.getAsString(PdfName.CERT);
//                    if (certStr == null) {
//                        certStr = sigDict.getAsArray(PdfName.CERT).getAsString(0);
//                    }
//                    if (certStr == null) {
//                        continue;
//                    }
////签章对应的证书
//                    X509CertParser certParser = new X509CertParser();
//                    certParser.engineInit(new ByteArrayInputStream(certStr.getBytes()));
//                    Collection<Certificate> certs = certParser.engineReadAll();
//                    if (certs == null || certs.size() == 0) {
//                        continue;
//                    }
//                    X509Certificate certificate = (X509Certificate) certs.iterator().next();
//                    if (certificate == null) {
//                        continue;
//                    }
//                    X500Principal principal = certificate.getSubjectX500Principal();
//                    if (principal == null) {
//                        continue;
//                    }
////签章对应的证书的所有者
////                    LdapName ldapDN = new LdapName(principal.getName());
////                    for (Rdn rdn : ldapDN.getRdns()) {
////                        if ("CN".equals(rdn.getType())) {
////                            result.add((String) rdn.getValue());
////                        }
////                    }
////                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
