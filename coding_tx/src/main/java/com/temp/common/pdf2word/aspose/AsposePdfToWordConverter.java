package com.temp.common.pdf2word.aspose;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Neither need license.xml, nor invoke getLicense() 
 * 
 * @author Louisling
 * @version 2018-07-27
 */
public class AsposePdfToWordConverter {

    public static void main(String[] args) {
        pdfToDoc("D:\\文档\\技术文档\\阿里巴巴Java开发手册终极版v1.3.0.pdf", "D:\\文档\\技术文档\\TEST.doc");
    }

    //doc/docx to PDF (use aspose-words-16.4.0-jdk16.jar, need no license.xml)
    //Support DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF
    //doc2pdf("c:/aaa.doc", "c:/aaa.pdf");
    public static void pdfToDoc(String wordPath, String pdfPath) {
        try {
            long start = System.currentTimeMillis();
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.DOC);
            os.close();
            System.out.println("It took time(seconds): " + ((System.currentTimeMillis() - start) / 1000.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}