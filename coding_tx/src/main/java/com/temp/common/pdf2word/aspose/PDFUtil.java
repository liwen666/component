package com.temp.common.pdf2word.aspose;
 
//import com.aspose.cells.Color;
import com.aspose.pdf.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * description: PDFUtil <br>
 *
 * @date: 2021/2/4 0004 上午 10:09 <br>
 * @author: William <br>
 * version: 1.0 <br>
 */
public class PDFUtil {
    private static InputStream license;
 
 
    public static void main(String[] args) throws Exception {
        pdf2word();
    }
 
 
 
 
    //多线程处理需要转换格式的文件
    public static void produceData(List<String> list) throws InterruptedException {
        //每个线程处理的数据，我这里只开了三个线程，
        int threadSize = ((list.size()-1)+3)/3;
        //int threadSize = 500;  可以每个线程处理500条数据
        int remainder = list.size() % threadSize;
        //线程数
        int threadNum = 0;
        if (remainder == 0) {
            threadNum = list.size() / threadSize;
        } else {
            threadNum = list.size() / threadSize + 1;
        }
        long begin = System.currentTimeMillis();
        //创建一个线程池
        ExecutorService eService = Executors.newFixedThreadPool(threadNum);
        List<Callable<String>> cList = new ArrayList<>();
        Callable<String> task = null;
        List<String> sList = null;
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                sList = list.subList(i * threadSize, list.size());
            } else {
                sList = list.subList(i * threadSize, (i + 1) * threadSize);
            }
            final List<String> nowList = sList;
            task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    nowList.forEach(filesPath -> {
                        if(filesPath.contains(".pdf")){
                            File file = new File(filesPath);
                            String paperName = file.getName();
                            paperName = paperName.substring(0,paperName.lastIndexOf("."));
                            String tempFilesPath = filesPath.substring(0,filesPath.lastIndexOf(File.separator));
                            tempFilesPath = tempFilesPath +"\\"+paperName+".docx";
                            System.out.println(tempFilesPath);
                            try {
                                saveAsWord(filesPath,tempFilesPath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return "ok";
                }
            };
            cList.add(task);
        }
        List<Future<String>> results = eService.invokeAll(cList);
        for (Future<String> str : results) {
            //System.out.println(str.get());
        }
        eService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("执行耗时：" + (end - begin));
    }
 
 
 
    public static void pdf2word() throws Exception {
        List<String> strings = new ArrayList<>();
        List<String> filesPaths = FileUtils.getFilesPaths("D:\\work\\temp\\中学学段2019科目二", strings);
        produceData(filesPaths);
    }
 
 
    //将PDF保存为word
    public static void saveAsWord(String targetFile,String newFile) throws Exception {
        File target = new File(targetFile);
        if(!target.exists()){
            target.mkdirs();
        }
        FileInputStream targetInputStream = new FileInputStream(target);
        //调用去水印的方法 读取license.xml文件
        if (!getLicense()) {
            System.out.println("获取验证失败");
        }
        Document targetDocument = new Document(targetInputStream);
        targetDocument.save(newFile, SaveFormat.DocX);
        targetInputStream.close();
        targetDocument.close();
    }
 
 
 
 
 
     //证书获取
    public static synchronized boolean getLicense() {
        boolean result = false;
        try {

//            String license2 = "<License>\n"
//                    + "  <Data>\n"
//                    + "    <Products>\n"
//                    + "      <Product>Aspose.Total for Java</Product>\n"
//                    + "      <Product>Aspose.Words for Java</Product>\n"
//                    + "    </Products>\n"
//                    + "    <EditionType>Enterprise</EditionType>\n"
//                    + "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n"
//                    + "    <LicenseExpiry>20991231</LicenseExpiry>\n"
//                    + "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n"
//                    + "  </Data>\n"
//                    + "  <Signature>111</Signature>\n"
//                    + "</License>";
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
            license = new ByteArrayInputStream(license2.getBytes("UTF-8"));

            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // 添加水印
//filepath：文件路径
//data：水印文字内容
    public static void addWatermark(String filepath,String data) {

        Document pdfDocument = new Document(filepath);
        TextStamp textStamp = new TextStamp(data);
        textStamp.setBackground(true);
        textStamp.setXIndent(100);
        textStamp.setYIndent(100);
        textStamp.setRotateAngle(50);

        textStamp.getTextState().setFont(FontRepository.findFont("Arial"));
        textStamp.getTextState().setFontSize(34.0F);
        textStamp.getTextState().setFontStyle(FontStyles.Italic);
        textStamp.getTextState().setForegroundColor(Color.getLightGray());

        TextStamp textStamp1 = new TextStamp(data);
        textStamp1.setBackground(true);
        textStamp1.setXIndent(300);//设置位置
        textStamp1.setYIndent(300);
        textStamp1.setRotateAngle(50);//设置旋转角度
        textStamp1.getTextState().setFont(FontRepository.findFont("Arial"));
        textStamp1.getTextState().setFontSize(34.0F);//设置字体大小
        textStamp1.getTextState().setFontStyle(FontStyles.Italic);
        textStamp1.getTextState().setForegroundColor(Color.getLightGray());//设置字体颜色

        TextStamp textStamp2 = new TextStamp(data);
        textStamp2.setBackground(true);
        textStamp2.setXIndent(500);
        textStamp2.setYIndent(500);
        textStamp2.setRotateAngle(50);
        textStamp2.getTextState().setFont(FontRepository.findFont("Arial"));
        textStamp2.getTextState().setFontSize(34.0F);
        textStamp2.getTextState().setFontStyle(FontStyles.Italic);
        textStamp2.getTextState().setForegroundColor(Color.getLightGray());


        PageCollection pages = pdfDocument.getPages();
        int size = pages.size();
        for (int i = 1; i <= size; i++) {
            pages.get_Item(i).addStamp(textStamp);
            pages.get_Item(i).addStamp(textStamp1);
            pages.get_Item(i).addStamp(textStamp2);
        }
        pdfDocument.save(filepath);
    }

}
