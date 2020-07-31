package com.temp.common.common.util;

import java.io.*;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class FileUtil {
    public static void main(String[] args) throws IOException {
        File f = new File("D:\\workspace\\test_workspace\\common\\src\\test\\java\\com\\temp\\common\\common\\util\\cvs.txt");
        FileInputStream fileInputStream = new FileInputStream(f);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        String lineTxt = null;

        while ((lineTxt = reader.readLine()) != null) {
            String[] names = lineTxt.split(" ");
            for (String name : names) {
                if(name.contains("_ev")){
                    System.out.print(name+",");
                }
            }
        }
    }
}
