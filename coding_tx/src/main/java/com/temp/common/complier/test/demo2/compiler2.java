package com.temp.common.complier.test.demo2;

import com.google.common.collect.Lists;
import com.temp.common.complier.tools.JavaParseObject;
import com.temp.common.complier.tools.JavaStringCompiler;
import com.temp.common.utils.PackageScanUtils;
import lombok.Cleanup;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/11  13:58
 */
public class compiler2 {
    public static void main(String[] args) throws IOException {
        String flinkJob = "hello";
        ArrayList<File> objects = Lists.newArrayList();
        PackageScanUtils.findResourceByPath("D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\complier\\test\\demo1", objects);
        String outPath = "D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\complier\\test\\out\\";
        objects.forEach(e -> {
            System.out.println(e.getName());
            System.out.println(e.getPath());
            try {
                @Cleanup FileInputStream fileInputStream = new FileInputStream(e);
                byte[] cache = new byte[fileInputStream.available()];
                fileInputStream.read(cache);
                String java = new String(cache, "utf-8");
                JavaParseObject build = new JavaParseObject().build(java);
                Map<String, byte[]> results = new JavaStringCompiler().compile(e.getName(), java);
                File outFile = new File(outPath + e.getName().split("\\.")[0] + ".class");
                FileOutputStream outputStream = new FileOutputStream(outFile);
                outputStream.write(results.entrySet().iterator().next().getValue());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
    }
}
