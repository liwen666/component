package com.temp.common.complier.test.demo2;

import lombok.Cleanup;

import java.io.*;

public class DelectSign {
    public static void main(String[] args) throws IOException {
/* 
删除本程序源代码的所有注释，并存入新文件
1.文件读取。使用FileReader 读取文件，并用BufferedReader逐行读入
2.判断注释（单行注释与多行注释）
3.处理注释(由于是逐行读入，对于单行注释直接用截取字符串的方式)
               (多行注释，需要判断是否结束，舍弃注释之间的所有内容)    
4.逐行写入文件(每处理一行写入一行，对于多行注释 直接跳过)
*/
        @Cleanup   FileReader fr = new FileReader("D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\complier\\test\\demo1\\HelloWord.java");
        @Cleanup   BufferedReader bufferedreader = new BufferedReader(fr);//asdasdasd
        @Cleanup  FileWriter fw = new FileWriter(new File("D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\complier\\test\\out\\HelloWord.java"));//asdasdasd
        @Cleanup BufferedWriter bw = new BufferedWriter(fw);
        char ch;
        String str;
        int index;
        boolean hasElseSign = false;
        while (null != (str = bufferedreader.readLine())) {
            str = str.trim();
            if (0 != str.length()) {
                if (hasElseSign == false) {
                    for (index = 0; index < str.length(); index++) {
                        ch = str.charAt(index);
                        if ((ch == '/')) {
                            if (str.charAt(index + 1) == '/') {//是否有单行注释，如有，截取字符串
                                str = str.substring(0, index);
                                break;
                            }
                            if (str.charAt(index + 1) == '*') {//是否有多行注释
                                hasElseSign = true;
                                break;
                            }
                        }
                    }
                    if (hasElseSign) continue;
                } else {
                    for (index = 0; index < str.length(); index++) {
                        ch = str.charAt(index);
                        if ((ch == '*') && (index < str.length() - 1) && (str.charAt(index + 1) == '/')) {
                            hasElseSign = false;
                            break;
                        }
                    }
                    continue;
                }
                bw.write(str);//写入文件 把str存入缓冲
                bw.newLine();//换行
                bw.flush();//立即写入 （把缓冲里的所有内容写入）
            }
        }
    }
}