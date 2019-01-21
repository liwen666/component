package com.temp.common.base.io.base;

import com.temp.common.base.io.high.EncodingDetect;

import java.io.*;

public class FileInputRead {
    public static void main(String[] args) throws IOException {
        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\base\\work.txt");
        InputStreamReader isr =new FileReader(f);
        byte[] cache = new byte[10];
        int rel = 0;
        char [] cacheCache = new char[2];
        while ((rel=isr.read(cacheCache))!=-1){
            System.out.println(rel);
            System.out.println(new String(cacheCache,0,rel));
        }
        System.out.println(rel);
      FileInputStream fis = new FileInputStream(f);
        rel=0;
        File fo = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\base\\worktarget.txt");
        if(fo.exists()){
            fo.createNewFile();
        }
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(fo);
        bis.mark(10);
        System.out.println("-------------------------");
        while ((rel=bis.read(cache))!=-1){

            System.out.println(rel);
            System.out.println(new String(cache,0,rel,"utf8"));
            fos.write(cache,0,rel);
        }

        bis.reset();
        byte [] newbyte = new byte[100];
        rel =0;
        while ((rel=bis.read(newbyte))!=-1){

            System.out.println(rel);
            System.out.println(new String(newbyte,0,rel,"utf8"));
            fos.write(newbyte,0,rel);
        }
        System.out.println(rel);



    }
}
