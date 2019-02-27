package com.temp.common.base.excel.sqlconversion;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelSqlConversion {

    public static void main(String[] args) {
        String filePath = "D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\excel\\sqlconversion\\bpmnindex.xlsx";
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
        int numberOfSheets = wookbook.getNumberOfSheets();
        System.out.println(numberOfSheets);
        //得到一个工作表
        for (int sheetnum = 0; sheetnum < numberOfSheets; sheetnum++) {
            Sheet sheet = wookbook.getSheetAt(sheetnum);
            System.out.println("sheet=====" + sheet.getSheetName());

            //获得表头
            Row rowHead = sheet.getRow(0);
            for (int h = 0; h < rowHead.getPhysicalNumberOfCells(); h++) {
                System.out.println(rowHead.getCell(h));
            }
            //判断表头是否正确
            System.out.println(rowHead.getPhysicalNumberOfCells());
            if (rowHead.getPhysicalNumberOfCells() != 3) {
                System.out.println("表头的数量不对!");
            }


            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();
            System.out.println("数据的总行数   " + totalRowNum);

            //要获得属性
            Object name = "";
            int latitude = 0;
            String immediateSql = "execute immediate 'create index ABCE on act_hi_taskinst(PROC_DEF_ID_,EXECUTION_ID_)';\n";
            //获得所有数据
            Map<String, List<String>> allSql = new HashMap<String, List<String>>();
            for (int i = 1; i <= totalRowNum; i++) {
                immediateSql = "";
                //获得第i行对象
                Row row = sheet.getRow(i);
                immediateSql = "execute immediate 'create index " + row.getCell(0) + " on " + row.getCell(2) + "(" + row.getCell(5) + ")';\n";
                List<String> strings = allSql.get(row.getCell(2).toString());
                System.out.println(row.getCell(2));
                if (strings == null) {
                    allSql.put(row.getCell(2).toString(), new ArrayList<>());
                    allSql.get(row.getCell(2).toString()).add(immediateSql);
                }else {
                    allSql.get(row.getCell(2).toString()).add(immediateSql);
                } ;

                System.out.println("创建索引 --->" + immediateSql);
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                System.out.println(physicalNumberOfCells);
                //获得获得第i行第0列的 String类型对象
                for (int j = 0; j < physicalNumberOfCells; j++) {
                    Cell cell = row.getCell(j);
                    try {
                        name = cell.getStringCellValue().toString();
                    } catch (Exception e) {
                        name = cell.getNumericCellValue();
                    }
                }
            }
            System.out.println(allSql);
            getImmediate(allSql);

        }

    }

    private static void getImmediate(Map<String, List<String>> allSql) {
        StringBuffer sb =new StringBuffer();
        int count =0;
        for(Map.Entry<String, List<String>> m:allSql.entrySet()){
            String tableName =  m.getKey();
            List<String> value = m.getValue();
            StringBuffer sql =new StringBuffer("select count(*) INTO i from USER_TABLES u where u.TABLE_NAME = '"+tableName+"';\n" +
                    "  if i = 1 then \n ");
                    for(String im :value){
                        sql.append(im+"\n");
                        count++;
                    }
            sql.append("else \n" +
                    "    dbms_output.put_line('"+tableName+"');\n" +
                    "end if;");
            sb.append(sql+"\n");
        }
        System.out.println(sb.toString());
        System.out.println("创建  "+count+"   条索引");
    }
}
