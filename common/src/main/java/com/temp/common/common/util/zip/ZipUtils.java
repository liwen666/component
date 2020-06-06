package com.temp.common.common.util.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class ZipUtils {
    public static void main(String[] args) {


        Map<String, String> data = new HashMap<String, String>() {{
            put("dddd", "aaaaaaa");
            put("eee/bbb", "hhhh");
        }};
        ZipOutputStream zipOut = null;
        try {
            File file = new File("D:\\idea2018workspace\\component_new\\common\\src\\main\\java\\com\\temp\\common\\common\\util\\aaa.zip");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            zipOut = new ZipOutputStream(fileOutputStream);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                zipOut.putNextEntry(new ZipEntry(entry.getKey()));
                    zipOut.write(entry.getValue().getBytes("utf-8"));
            }
            zipOut.closeEntry();
            zipOut.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
