package com.temp.common.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileIteration {
    public static Logger logger = LoggerFactory.getLogger(FileIteration.class);

    public static void main(String[] args) {
//        File f = new File("D:\\component\\component\\coding_tx\\src\\main\\java");
        File f = new File("D:\\component\\component");
//        File f = new File("D:\\web");
        long l1 = System.currentTimeMillis();
        showFile(f);
        long l2 = System.currentTimeMillis();
        showFile2(f);
        logger.debug("递归耗时-------{}",l2-l1);
        logger.debug("循环耗时-------{}",System.currentTimeMillis()-l2);
    }

    private static void showFile2(File file) {
        logger.info(file.getName() + "-------------循环");
        boolean flag = true;
        List<File> dir = new ArrayList<>();
        int boot=0;
        while (flag) {
            if (file.isDirectory()) {
                if(dir.size()>boot){
                    file= dir.get(boot);
                    boot+=1;
                }
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                  logger.info((files[i].getName()+"循环---------------"));
                    if (files[i].isDirectory()) {
                        dir.add(files[i]);
                    }
                }
                if(!(dir.size()>boot)){
                    flag=false;
                }
            } else {
                flag = false;
            }
        }
//        System.out.println(dir.size()+"----------------------dir  ");
    }

    private static void showFile(File file) {
        logger.info(file.getName()+"-------------递归");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    showFile(f);
                } else {
                    logger.info(f.getName()+"-------------递归");
                }
            }
        }
    }
}
