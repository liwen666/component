package com.temp.common.base.io.whitefile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WhiteUrlUtil {
    public static long lastModifyTime=0;
    public static JSONArray objects = null;
    public static void main(String[] args) {
//        List<String> urlArray = new ArrayList<String>();
//        urlArray.add("/fp");
//        urlArray.add("kkkk");
//        String string = JSON.toJSON(urlArray).toString();
//        JSONArray objects = JSON.parseArray(string);
        System.out.println(verifyUrl("kkkk")+"-----");
        System.out.println(verifyUrl("kkk")+"-----");
    }

    public static boolean verifyUrl(String url) {
        String basePath = System.getProperty("user.dir");
        String path = basePath+"/"+"src/resources/whiteurl.json";
        File file = new File(path);
//        System.out.println(getModifiedTime_2(file));
        if (!(file.lastModified()==lastModifyTime)) {
            lastModifyTime=file.lastModified();
            FileInputStream fis =null ;
            try {
                fis=  new FileInputStream(file);
                byte [] cache = new byte[1024*10];
                int read = fis.read(cache);
                String urlString = new String(cache,0,read);
                 objects= JSON.parseArray(urlString);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        if(objects.contains(url)){
            return true;
        }
        return false;
    }
    public static String getModifiedTime_2(File file){
        Calendar cal = Calendar.getInstance();
        String timechange="";
        long time = file.lastModified();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换文件最后修改时间的格式
        cal.setTimeInMillis(time);
        timechange = formatter.format(cal.getTime());
        return timechange;
    }


}
