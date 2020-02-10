package com.temp.common.base.excel.table;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class CellStyleUtils {
    public static CellStyle getNomalCellStyle(SXSSFWorkbook wb) {
        // 创建两种单元格格式
        CellStyle nomal = wb.createCellStyle();
        // 创建两种字体
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("方正仿宋简体");

        // 设置第一种单元格的样式（用于列名）
        nomal.setFont(font);
        nomal.setBorderLeft(BorderStyle.THIN);
        nomal.setBorderRight(BorderStyle.THIN);
        nomal.setBorderTop(BorderStyle.THIN);
        nomal.setBorderBottom(BorderStyle.THIN);
        nomal.setVerticalAlignment(VerticalAlignment.CENTER);
        nomal.setAlignment(HorizontalAlignment.CENTER);
        return nomal;
    }

    public static CellStyle getTitileStyle(SXSSFWorkbook wb) {
        // 创建两种单元格格式
        CellStyle title = wb.createCellStyle();
        // 创建两种字体
        Font font = wb.createFont();
        // 创建第一种字体样式（用于列名）
        font.setFontHeightInPoints((short) 20);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("黑体");
//        font.setBold(true);
        // 设置第一种单元格的样式（用于列名）
        title.setFont(font);
        title.setBorderLeft(BorderStyle.THIN);
        title.setBorderRight(BorderStyle.THIN);
        title.setBorderTop(BorderStyle.THIN);
        title.setBorderBottom(BorderStyle.THIN);
        title.setAlignment(HorizontalAlignment.CENTER);
        return title;
    }

}
