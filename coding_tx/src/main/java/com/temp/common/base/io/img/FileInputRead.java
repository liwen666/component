package com.temp.common.base.io.img;

import com.temp.common.base.io.high.EncodingDetect;

import java.io.*;

public class FileInputRead {
    public static void main(String[] args) throws IOException {
//        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\img\\iso8859.png");
        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\img\\1547802467(1).png");
        InputStreamReader isr =new FileReader(f);
        byte[] cache = new byte[1000];
        int rel = 0;
        char [] cacheCache = new char[2];
        while ((rel=isr.read(cacheCache))!=-1){
            System.out.println(rel);
            System.out.println(new String(cacheCache,0,rel));
        }
        System.out.println(rel);
      FileInputStream fis = new FileInputStream(f);
        rel=0;
        File fo = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\img\\utf8.png");
        if(fo.exists()){
            fo.createNewFile();
        }
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(fo);
        bis.mark(1000000000);
        System.out.println("-------------------------");
        while ((rel=bis.read(cache))!=-1){
            System.out.println(rel);
            System.out.println(new String(cache,0,rel,"utf8"));
            fos.write(cache,0,rel);
        }
        System.out.println("编码==============="+ EncodingDetect.getJavaEncode(cache));

        bis.reset();
        byte [] newbyte = new byte[1000];
        rel =0;
        while ((rel=bis.read(newbyte))!=-1){

            System.out.println(rel);
            System.out.println(new String(newbyte,0,rel,"utf8"));
            fos.write(newbyte,0,rel);
        }
        System.out.println("编码==============="+ EncodingDetect.getJavaEncode(newbyte));

        bis.reset();
        rel = 0;
        char[] charArr = new char[100];
        InputStreamReader isrs = new InputStreamReader(bis);
        OutputStreamWriter osw = new OutputStreamWriter(fos,"utf8");
        while ((rel=isrs.read(charArr))!=-1){

            System.out.println(rel);
            System.out.println(new String(newbyte,0,rel,"utf8"));
            osw.write(charArr,0,rel);
        }
        System.out.println(rel);



    }
    static class  TxEncode{
        public static void main(String[] args) throws IOException {
//            File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\img\\1547802467(1).png");
//            File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\img\\iso8859.png");
            File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io\\img\\utf8.png");
            FileInputStream fis = new FileInputStream(f);
            byte[] cache = new byte[1000];
            int rel = 0;
            char [] cacheCache = new char[2];
            while ((rel=fis.read(cache))!=-1){
                System.out.println(rel);
            }
            System.out.println("编码==============="+ EncodingDetect.getJavaEncode(cache));

        }
    }
}
