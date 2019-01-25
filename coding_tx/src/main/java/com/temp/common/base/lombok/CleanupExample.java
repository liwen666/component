package com.temp.common.base.lombok;

import lombok.Cleanup;
import java.io.*;
public class CleanupExample {
    public static void main(String[] args) throws IOException {
        args=new String[2];
        args[0]="D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\lombok\\text.txt";
        args[1]="D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\lombok\\text1.txt";
        System.out.println(args[0]);
        @Cleanup InputStream in = new FileInputStream(args[0]);
        @Cleanup OutputStream out = new FileOutputStream(args[1]);
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }
}