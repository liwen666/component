package com.temp.common.base.fileupload.base;


import java.io.*;

public class ByteTx {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "我们";
        byte[] bytes = str.getBytes();
        for (byte b : bytes) {
//            Byte by = new Byte(b);
//            System.out.println(by.toString());
            System.out.println(b);
        }

        byte[] gbks = str.getBytes("gbk");
        System.out.println(new String(gbks, "gbk") + "-------gbk");
        System.out.println(bytes);
        String gbk = new String(bytes, "gbk");
        String utf8 = new String(bytes, "utf8");
        String gb2312 = new String(bytes, "gb2312");
        String iso = new String(bytes, "iso-8859-1");

        System.out.println(gbk + "---" + utf8 + "---" + gb2312 + "---" + iso);
        System.out.println("------------GBK------------");
        for (byte b : gbk.getBytes("GBK")) {
            System.out.println(b);
        }
        System.out.println("------------utf8------------");
        for (byte b : utf8.getBytes()) {
            System.out.println(b);
        }
        System.out.println("----------GB2312--------------");
        for (byte b : gb2312.getBytes("GB2312")) {
            System.out.println(b);
        }

        System.out.println(new String(gb2312.getBytes("GB2312"), "utf8"));
        System.out.println("------------------------");
        for (byte b : iso.getBytes()) {
            System.out.println(b);
        }
        System.out.println("------------------------");
        for (byte b : iso.getBytes("iso-8859-1")) {
            System.out.println(b);
        }
        System.out.println(new String(iso.getBytes("iso-8859-1")));

        boolean b = java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(str);
        System.out.println(b);
        boolean b1 = java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(str);
        System.out.println(b1);
        boolean b2 = java.nio.charset.Charset.forName("gb2312").newEncoder().canEncode(str);
        System.out.println(b2);
        boolean iso1 = java.nio.charset.Charset.forName("iso-8859-1").newEncoder().canEncode(str);
        System.out.println(iso1);
    }


    static class ByteTx2 {
        public static void main(String[] args) throws UnsupportedEncodingException {
            String str = "我的。";
            byte[] gb2312s = str.getBytes("gb2312");
            System.out.println(new String(gb2312s,"gb2312"));
            System.out.println(new String(gb2312s,"utf8"));


        }
    }
    static class FileEncode{
        public static void main(String[] args) throws IOException {
            FileInputStream f= new FileInputStream("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\每周任务.txt");

            InputStreamReader isr = new InputStreamReader(f,"utf8");
//            InputStreamReader isr = new InputStreamReader(f,"gbk");
            byte []b = new byte[1024];
            char []b2 = new char[1024];
            int len = 0;
            while (isr.read(b2) != -1){

                isr.read(b2);
            }


            isr.close();
            f.close();
            System.out.println(new String(b,"utf8") );
            System.out.println(new String(b,"gbk") );
            System.out.println(new String(b2) +"----------");
            System.out.println("----------");
            new String(b2,0,20);

        }
    }

    static class DecodeFile{
        public static void main(String[] args) throws IOException {
            FileInputStream f= new FileInputStream("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\每周任务.txt");

            byte []b = new byte[1024];
            int len = 0;
            while (f.read(b)!=-1){
                f.read(b);
            }
            f.close();

            System.out.println(new String(b,"utf8") );
            System.out.println("----------------------");
            String utf8 = new String(b, "utf8");
            byte[] gbks = utf8.getBytes("gbk");
            System.out.println(new String(gbks,"gbk") );


        }
    }
    static class DecodeGbkFile{
        public static void main(String[] args) throws IOException {
            FileInputStream f= new FileInputStream("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\gbk.txt");

            byte []b = new byte[1024];
            int len = 0;
            while (f.read(b)!=-1){
                f.read(b);
            }
            f.close();

            System.out.println(new String(b,"gbk") );
            System.out.println("----------------------");
            String gbk = new String(b, "gbk");
            byte[] gbks = gbk.getBytes("utf8");
            System.out.println(new String(gbks,"utf8") );
            byte[] iso = gbk.getBytes("gb2312");
            System.out.println(new String(iso,"gb2312") );
            FileOutputStream o= new FileOutputStream("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\gbktoutf8.txt");
            o.write(gbks);
        }
    }
}
