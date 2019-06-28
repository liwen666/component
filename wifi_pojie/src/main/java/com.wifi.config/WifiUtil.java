package com.wifi.config;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author tx
 * @since 2019/5/26 23:40
 */
public class WifiUtil {
    private static List<String> execute(String cmd , String filePath)
    {
        Process process = null;
        List<String> result = new ArrayList<String>();
        try{
            if(filePath!=null){
                    process=Runtime.getRuntime().exec(cmd,null,new File(filePath));
            }else{
                process=Runtime.getRuntime().exec(cmd);
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                result.add(line);
            }
        }catch (Exception e){

        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("******************************************************");
       //连接wifi  netsh wlan connect name=SSIDNAME
//        添加配置文件
       // netsh wlan add profile filename=WLAN-TEMP.xml
        List<String> result = execute("netsh wlan connect name=\"Honor Note10\"", null);
//        String javaEncode = EncodingDetect.getJavaEncode(result.get(0).getBytes());
//        System.out.println(javaEncode);
        System.out.println(JSON.toJSONString(result));
        List<String> ping = execute("ping www.baidu.com", null);
//        String javaEncode = EncodingDetect.getJavaEncode(result.get(0).getBytes());
//        System.out.println(javaEncode);
        System.out.println(JSON.toJSONString(ping));
//        System.out.println(execute("ping baidu.com",null));
    }
}
