package com.temp.common.common.util.zip;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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

    /**
     * 解析zip压缩包
     * @param fileInputStream
     * @return
     */
    public Map<String, String> importData(InputStream fileInputStream) {
        Map<String, String> data = new ConcurrentHashMap<>();
        ZipInputStream zipInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry entry = zipInputStream.getNextEntry();

            byteArrayOutputStream = new ByteArrayOutputStream();
            while (entry != null) {
                String name = entry.getName();
                byte[] cache = new byte[1024];
                int read = zipInputStream.read(cache);
                while (read != -1) {
                    byteArrayOutputStream.write(cache, 0, read);
                    read = zipInputStream.read(cache);
                }
                data.put(name, new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF8")));
                if (name.startsWith("OBJECT"))
                {
                    System.out.println(data.get(name));
                }
                zipInputStream.closeEntry();
                byteArrayOutputStream.reset();
                entry = zipInputStream.getNextEntry();

            }
        } catch (IOException ex) {
//            logger.error("解析上传文件出错！");
        } finally {
            if (null != zipInputStream) {
                try {
                    zipInputStream.close();

                } catch (IOException e) {
                }
            }
            if (null != byteArrayOutputStream) {
                try {
                    zipInputStream.close();

                } catch (IOException e) {
                }
            }
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }

        }
        return data;
    }
}
