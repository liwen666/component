package com.temp.common.pdf2word.aspose;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

/**
 * Neither need license.xml, nor invoke getLicense()
 *
 * @author Louisling
 * @version 2018-07-27
 */
public class AsposeWord2PdfConverter {

    public static void main(String[] args) {

        getLicense2();
//        doc2pdf("c:/aaa.doc", "c:/aaa.pdf");
//        doc2pdf("c:/bbb.docx", "c:/bbb.pdf");
        doc2pdf("D:\\文档\\技术文档\\大数据-数据平台代码规范.docx", "D:\\文档\\技术文档\\大数据-数据平台代码规范.pdf");
    }

    //doc/docx to PDF (use aspose-words-16.4.0-jdk16.jar, need no license.xml)
    //Support DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF
    //doc2pdf("c:/aaa.doc", "c:/aaa.pdf");

    //证书获取
    public static synchronized boolean getLicense2() {
        boolean result = false;
        try {

            String license2 = "<License>\n" +
                    "    <Data>\n" +
                    "        <Products>\n" +
                    "            <Product>Aspose.Total for Java</Product>\n" +
                    "            <Product>Aspose.Words for Java</Product>\n" +
                    "        </Products>\n" +
                    "        <EditionType>Enterprise</EditionType>\n" +
                    "        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "        <LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "    </Data>\n" +
                    "    <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                    "</License>";
            ByteArrayInputStream license = new ByteArrayInputStream(license2.getBytes("UTF-8"));
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String wordPath, String pdfPath) {
        try {
            long start = System.currentTimeMillis();
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();
            System.out.println("It took time(seconds): " + ((System.currentTimeMillis() - start) / 1000.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}