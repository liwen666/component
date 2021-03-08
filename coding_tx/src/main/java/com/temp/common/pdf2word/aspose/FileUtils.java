package com.temp.common.pdf2word.aspose;
 
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * description: FileUtils  文件操作工具类<br>
 *
 * @date: 2020/11/17 0017 下午 5:06 <br>
 * @author: William <br>
 * version: 1.0 <br>
 */
public class FileUtils {
 
 
 
    //因为我这个是临时用所以没有考虑并发，如果并发自己修改一下就好了
    public static List<String> resultList = new ArrayList<>();
 
 
    /**
     *@description: 通过文件路径，修改该路径下所有文件的名字
     * @param path  文件夹路径
     * @return:
     * @author: William
     * @date 2019/8/8 14:52
     */
    public static List<String> getFilesPaths(String path,List<String> stringList){
        File file = new File(path);
        if(file.exists()){
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        getFilesPaths(file2.getAbsolutePath(),stringList);
                    } else {
                        String filePath = file2.getAbsolutePath();
                        stringList.add(filePath);
                    }
                }
            }
        }else{
            System.out.println("该路径不存在");
        }
        return stringList;
    }
 
}