package com.temp.common.base.io.base;

import java.io.*;

public class ByteFileWrite {
    public static void main(String[] args) throws IOException {
        File f  = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\base\\byte");
        if(!f.exists()){f.createNewFile();}
        FileWriter fw = new FileWriter(f.getAbsoluteFile());
        fw.write(111);
        fw.write("2222222dddfds");
        fw.flush();;
        fw.close();
        FileOutputStream fo = new FileOutputStream(f);
        DataOutputStream dos = new DataOutputStream(fo);
        dos.write("jjjjj".getBytes());
        dos.flush();
        dos.close();
    }
}
