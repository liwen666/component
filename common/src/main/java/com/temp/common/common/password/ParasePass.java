package com.temp.common.common.password;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParasePass {
    public static void main(String[] args) throws IOException {
        File f = new File("D:\\component\\component\\common\\src\\main\\java\\com\\temp\\common\\common\\password\\pass.json");
        FileInputStream fis = new FileInputStream(f);
        byte[] pa = new byte[1024*1024];
        int read = fis.read(pa);
        String s = new String(pa, 0, read);

        HashMap<Integer, List<String>> integerListHashMap = JSON.parseObject(s, new TypeReference<HashMap<Integer, List<String>>>() {
        });
//        HashMap hashMap = JSON.parseObject(s, new HashMap<Integer, List<String>>().getClass());
        System.out.println(integerListHashMap.get(4));

    }
}
