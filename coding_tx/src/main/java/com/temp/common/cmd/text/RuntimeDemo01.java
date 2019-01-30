package com.temp.common.cmd.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RuntimeDemo01 {

    public static void main(String[] args) {
        String s;
        StringBuilder sb = new StringBuilder();
        InputStream fis = null;
        try {
             Process process = Runtime.getRuntime().exec("ping localhost");
//               Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", XXXX});
               // 注意，我将原来的15行注释掉了，变成了下面的写法。声明，我调用的command是Lunix下的命令，如果你用的是windows的话，不需要这么写。
               // 为什么要使用这样的写法，因为项目需要考虑到单引号双引号等，转换加/的原因。
            fis = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis,"gbk"));
            while((s=bufferedReader.readLine()) != null) {
                sb.append(s);
                   //sb.append(\n);
            }
            System.out.println(sb.toString());
            process.waitFor();
            System.out.println(process.exitValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}