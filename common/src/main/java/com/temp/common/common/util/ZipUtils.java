package com.temp.common.common.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        }};
        ZipOutputStream zipOut = null;
        ByteArrayInputStream bin = null;
        try {
            File file = new File("D:\\idea2018workspace\\component_new\\common\\src\\main\\java\\com\\temp\\common\\common\\util\\aaa.zip");
            if (!file.exists()) {
                file.createNewFile();
            }


            FileOutputStream fileOutputStream = new FileOutputStream(file);

            zipOut = new ZipOutputStream(fileOutputStream);

            for (Map.Entry<String, String> entry : data.entrySet()) {

                //bin = new ByteArrayInputStream(getCrypt(entry.getValue()).getBytes(UTF8));
                byte[] bytes = entry.getValue().getBytes(StandardCharsets.UTF_8);
                bin = new ByteArrayInputStream(bytes);
                zipOut.putNextEntry(new ZipEntry(entry.getKey()));

                byte[] buf = new byte[1024];
                int len;
                while ((len = bin.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }
                bin.close();
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
