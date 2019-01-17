package com.temp.common.base.excel.importexe;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ImportExecl {
    /**
     * 读取出filePath中的所有数据信息
     */
    public static void main(String[] args) {
        String filePath = "D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\excel\\base\\lw.xls";
//        String filePath = "D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\excel\\base\\测试例子.xls";
        getDataFromExcel(filePath);
    }

    public static void getDataFromExcel(String filePath) {

        //判断是否为excel类型文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
            return;
        }

        FileInputStream fis = null;
        Workbook wookbook = null;

        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);//得到工作簿
        } catch (Exception ex) {
            //ex.printStackTrace();
            try {
                //2007版本的excel，用.xlsx结尾
                fis = new FileInputStream(filePath);
                wookbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);

        //获得表头
        Row rowHead = sheet.getRow(0);
         for(int h=0;h<rowHead.getPhysicalNumberOfCells();h++)
         {
             System.out.println(rowHead.getCell(h));
         }
        //判断表头是否正确
        System.out.println(rowHead.getPhysicalNumberOfCells());
        if (rowHead.getPhysicalNumberOfCells() != 3) {
            System.out.println("表头的数量不对!");
        }


        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();

        //要获得属性
        Object name = "";
        int latitude = 0;

        //获得所有数据
        for (int i = 1; i <= totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            System.out.println(physicalNumberOfCells);
            //获得获得第i行第0列的 String类型对象
            for(int j=0;j<physicalNumberOfCells;j++){
                Cell cell = row.getCell(j);
                try {
                    name = cell.getStringCellValue().toString();
                } catch (Exception e) {
                    name = cell.getNumericCellValue();
                }
                System.out.println("名字：" + name );
            }
        }
    }
}
