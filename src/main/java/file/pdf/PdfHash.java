package file.pdf;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileOutputStream;
import java.security.Security;
import java.util.Date;
import java.util.List;

public class PdfHash {
    public static void main(String[] args) {
        getHash();
    }

    public static void getHash(){
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        try{
            //读取数据
            PdfReader reader = new PdfReader("E:/files/test/test2.pdf");
            //
            AcroFields acroFields = reader.getAcroFields();
            //获取
            List<String> names = acroFields.getSignatureNames();
            FileOutputStream fout = new FileOutputStream("E:/files/test/testout"+ new Date().getTime() +".txt");
            for (String name : names){
                System.out.println();
                System.out.println("Signature name: " + name);
                System.out.println(acroFields.extractRevision(acroFields.getField(name)));
                fout.write((name+"\r\n").getBytes());
                System.out.println(acroFields.verifySignature(name).getTimeStampToken());
                System.out.println(acroFields.getField(name));
                System.out.println(acroFields.getFieldRichValue(name));
                System.out.println(acroFields.getNormalAppearance(name).getGeneration());
            }
        }catch (Exception e){

        }


    }
}
