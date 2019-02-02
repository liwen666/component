package com.temp.common.cmd;

import java.io.InputStream;
 
public class Test {
 
public static void main(String[] args) {
 
    String path = "mspaint";
 
    Runtime run = Runtime.getRuntime();
 
    try {
 
        Process process = run.exec("cmd.exe /c start " + path);
//        Process process = run.exec("cmd.exe /k start " + path);

        InputStream in = process.getInputStream();
        byte[] a = new byte[1024];
        int len =0;
        while ((len=in.read(a))!= -1) {
            System.out.println(len);
            for(byte b:a){
                System.out.println(b);

            }

        }
        in.close();
        process.waitFor();
 
    } catch (Exception e) {
 
        e.printStackTrace();
 
    }
 
}
 
}
