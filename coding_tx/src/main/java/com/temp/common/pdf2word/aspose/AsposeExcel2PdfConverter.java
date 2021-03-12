package com.temp.common.pdf2word.aspose;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

/**
 * Note: Even if no license.xml, but it is also OK, just need invoke getLicense(), although the license InputStream get null
 *
 * @author Louisling
 * @version 2018-07-27
 */
public class AsposeExcel2PdfConverter {

    public static void main(String[] args) {
//        xls2pdf("c:/aaa.xls", "c:/aaa.pdf");
//        xls2pdf("c:/bbb.xlsx", "c:/bbb.pdf");
        xls2pdf("D:\\文档\\开发文档\\数据管理平台任务细分.xlsx", "D:\\文档\\开发文档\\数据管理平台任务细分.pdf");
    }

    public static void xls2pdf(String excelPath, String pdfPath) {
        if (!getLicense()) {
            System.err.print("Failed to load license");
            return;
        }

        try {
            long start = System.currentTimeMillis();
            Workbook workbook = new Workbook(excelPath);
            FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
            workbook.save(fileOS, SaveFormat.PDF);

            System.out.println("It took time(seconds): " + ((System.currentTimeMillis() - start) / 1000.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If no license, will display:
     * Evaluation Only. Created with Aspose.Cells for Java. Copyright 2003 - 2018 Aspose Pty Ltd.
     */
    protected static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeWord2PdfConverter.class.getClassLoader().getResourceAsStream("\\license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}