package com.temp.common.base.fileupload.base;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEncode {
    public static void main(String[] args) throws IOException {
        String fileEncode=EncodingDetect.getJavaEncode("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\gbk.txt");
        String fileContent= FileUtils.readFileToString(new File("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\gbk.txt"),fileEncode);
        System.out.println(fileEncode);
        System.out.println(fileContent);

        System.out.println(EncodingDetect.getJavaEncode("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\fileupload\\WEB-INF\\temp\\ss"));
    }
}
