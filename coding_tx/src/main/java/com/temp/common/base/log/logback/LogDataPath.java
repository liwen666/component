package com.temp.common.base.log.logback;

import ch.qos.logback.core.PropertyDefinerBase;

import java.net.URL;

public class LogDataPath extends PropertyDefinerBase {
    private String dataPath;

    @Override
    public String getPropertyValue() {
        URL resource = LogDataPath.class.getResource("");
        String path = resource.getPath();
       String dataPath= path.substring(1,path.length()-8)+"logdata";
        System.out.println(dataPath);
//        return "D:/component/component/coding_tx/src/main/java/com/temp/common/base/log/logdata";
        System.out.println(System.getProperty("user.dir"));
//        使用系统目录存放日志信息
        return System.getProperty("user.dir");
    }

    public static void main(String[] args) {
        URL resource = LogDataPath.class.getResource("");
        String dataPath = resource.getPath();
        System.out.println(dataPath.substring(1,dataPath.length()-8)+"logdata");
//        URL resource1 = LogDataPath.class.getClassLoader().getResource("");
//        System.out.println(resource1.getPath());
        String path=System.getProperty("user.dir");
        System.out.println(path);
        System.out.println(System.getProperty("user.home"));
    }
}
