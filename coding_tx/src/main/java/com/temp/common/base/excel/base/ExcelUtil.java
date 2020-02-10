package com.temp.common.base.excel.base;

import java.util.List;
import java.util.Map;

import com.temp.common.base.excel.table.CellStyleUtils;
import org.apache.poi.ss.examples.CellStyleDetails;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 生成Excel文件的工具类
 * @author libo
 */
public class ExcelUtil {
    
    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel工作簿
        SXSSFWorkbook wb = new SXSSFWorkbook(100);//在内存中只保留100行记录,超过100就将之前的存储到磁盘里
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString()); 
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth(i, (int) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow(0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short)20);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBold(true);
//        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short)10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setAlignment(HorizontalAlignment.CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(BorderStyle.THIN);
        cs2.setBorderRight(BorderStyle.THIN);
        cs2.setBorderTop(BorderStyle.THIN);
        cs2.setBorderBottom(BorderStyle.THIN);
        cs2.setAlignment(HorizontalAlignment.CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (int i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for(int j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }


    /**
     * 创建excel文档，
     * @param list 数据
     * @param wb*/
    public static Workbook createNewSheet(List<Map<String, Object>> list, SXSSFWorkbook wb) {
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        sheet.setColumnWidth(0,100*35);
        sheet.setColumnWidth(1,100*25);
        sheet.setColumnWidth(2,100*100);
        sheet.setColumnWidth(3,100*100);
        sheet.setColumnWidth(4,100*40);
        sheet.setColumnWidth(5,100*60);
        // 创建第一行
        Row title = sheet.createRow(0);
        Map<String, Object> stringObjectMap1 = list.get(1);
        String[]titleVal= (String[]) stringObjectMap1.get(stringObjectMap1.keySet().iterator().next());
        for(int i=0;i<titleVal.length;i++){
            Cell cell = title.createCell(i);
            cell.setCellValue(titleVal[i]);
            cell.setCellStyle(CellStyleUtils.getTitileStyle(wb));
        }



        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
        // 行和列都是从0开始计数，且起始结束都会合并
        // 这里是合并excel中日期的两行为一行
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
        CellRangeAddress region1 = new CellRangeAddress(2, 3, 5, 5);
        CellRangeAddress region8 = new CellRangeAddress(4, 4, 2, 4);
        CellRangeAddress region12 = new CellRangeAddress(4, 4, 0, 1);
        CellRangeAddress region13 = new CellRangeAddress(5, 5, 0, 1);
        CellRangeAddress region14 = new CellRangeAddress(6, 6, 0, 1);
        CellRangeAddress region15 = new CellRangeAddress(7, 7, 0, 1);
        CellRangeAddress region9 = new CellRangeAddress(5, 5, 2, 4);
        CellRangeAddress region10 = new CellRangeAddress(6, 6, 2, 4);
        CellRangeAddress region11 = new CellRangeAddress(7, 7, 2, 4);
        CellRangeAddress region2 = new CellRangeAddress(4, 6, 5, 5);
        CellRangeAddress region3 = new CellRangeAddress(8, 8, 0, 5);
        CellRangeAddress region4 = new CellRangeAddress(9, 9, 1, 2);
        CellRangeAddress region5 = new CellRangeAddress(10, 10, 0, 5);
        CellRangeAddress region6 = new CellRangeAddress(11, 11, 0, 5);
        CellRangeAddress region7 = new CellRangeAddress(12, 12, 0, 5);
        sheet.addMergedRegion(region);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        sheet.addMergedRegion(region3);
        sheet.addMergedRegion(region4);
        sheet.addMergedRegion(region5);
        sheet.addMergedRegion(region6);
        sheet.addMergedRegion(region7);
        sheet.addMergedRegion(region8);
        sheet.addMergedRegion(region9);
        sheet.addMergedRegion(region10);
        sheet.addMergedRegion(region11);
        sheet.addMergedRegion(region12);
        sheet.addMergedRegion(region13);
        sheet.addMergedRegion(region14);
        sheet.addMergedRegion(region15);

        //设置每行每列的值
        for (int i = 2; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i-1);
            row1.setHeight((short) 600);
            // 在row行上创建一个方
            Map<String, Object> stringObjectMap = list.get(i);
            String[] value = (String[]) stringObjectMap.get(stringObjectMap.keySet().iterator().next());
            for(int j=0;j<value.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(value[j]);
                cell.setCellStyle(CellStyleUtils.getNomalCellStyle(wb));
            }
        }
        return wb;
    }

}