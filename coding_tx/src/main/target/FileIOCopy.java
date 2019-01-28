package com.temp.common.base.io;

import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

public class FileIOCopy {
    public static void main(String[] args) throws IOException {
//        FileCopyUtils
        File s = new File("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\io");
        File d = new File("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\iotarget");
        FileSystemUtils.copyRecursively(s,d);
    }


}

