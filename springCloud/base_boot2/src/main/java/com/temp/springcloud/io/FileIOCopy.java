package com.temp.springcloud.io;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class FileIOCopy {
    public static void main(String[] args) throws IOException {
//        FileCopyUtils
        File s = new File("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\java\\com\\temp\\springcloud\\io");
        File d = new File("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\iotarget");
        FileSystemUtils.copyRecursively(s,d);
    }


}

