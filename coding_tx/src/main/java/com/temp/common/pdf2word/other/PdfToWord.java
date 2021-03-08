package com.temp.common.pdf2word.other;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class PdfToWord {
    public static void main(String[] args) {
        try {
            String pdfFile = "D:\\文档\\技术文档\\阿里巴巴Java开发手册终极版v1.3.0.pdf";
            PDDocument doc = PDDocument.load(new File(pdfFile));
            int pagenumber = doc.getNumberOfPages();
            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
            String fileName = pdfFile + ".doc";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");
//            PDFTextStripper stripper = new PDFTextStripper();

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(1);// 设置转换的开始页
            stripper.setEndPage(pagenumber);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}