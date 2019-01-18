package com.temp.common.base.JDBC;

import java.io.BufferedInputStream;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 import java.util.Iterator;
 import java.util.Properties;

 public class PropertyTest {
     public static void main(String[] args) {
         Properties prop = new Properties();
         try{
             //读取属性文件a.properties
             InputStream in = new BufferedInputStream (new FileInputStream("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\jdbc.properties"));
             prop.load(in);     ///加载属性列表
             Iterator<String> it=prop.stringPropertyNames().iterator();
             while(it.hasNext()){
                 String key=it.next();
                 System.out.println(key+":"+prop.getProperty(key));
             }
             in.close();

             ///保存属性到b.properties文件
//             FileOutputStream oFile = new FileOutputStream("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\target.properties", true);//true表示追加打开
             FileOutputStream oFile = new FileOutputStream("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\JDBC\\target.properties");//true表示追加打开
             oFile.flush();
             prop.setProperty("phone", "10086");
             prop.store(oFile, "The New properties file");
             oFile.close();
         }
         catch(Exception e){
             System.out.println(e);
         }
     }
 }