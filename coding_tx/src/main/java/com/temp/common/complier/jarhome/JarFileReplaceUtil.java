package com.temp.common.complier.jarhome;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * <p>
 * 描述jar文件替换工具
 * </p>
 *
 * @author lw
 * @since 2021/4/3 17:29
 */

public class JarFileReplaceUtil {

    /**
     * @param jarPath    jar包所在绝对路径
     * @param sourcePath confPath配置文件绝对路径
     * @param destPath   配置文件jar包 位置config/sysconfig.properties
     * @throws IOException
     */
    public static void replaceFile(String jarPath, String sourcePath, String destPath) throws IOException {
        String jarName = jarPath.substring(jarPath.lastIndexOf(File.separator), jarPath.lastIndexOf("."));
        File file = new File(jarPath);
        File destFile = new File(jarPath.substring(0, jarPath.lastIndexOf(File.separator)) + jarName + "_cp.jar");
        file.renameTo(destFile);
        // 将jar文件名重命名为jarName_cp.jar

        JarFile jarFile = null;
        InputStream in = null;
        JarOutputStream out = null;
        try {
            jarFile = new JarFile(destFile);
            out = new JarOutputStream(new FileOutputStream(file));
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                InputStream in1 = null;
                try {
                    String jarEntryName = jarEntry.getName();
                    System.out.println(jarEntryName);
                    if (destPath.equals(jarEntryName)) {
                        continue;
                    }
                    in1 = jarFile.getInputStream(jarEntry);
                    out.putNextEntry(jarEntry);
                    copyFile(in1, out);
                } finally {
                    if (isaBoolean(in1)) {
                        try {
                            in1.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
            JarEntry jarEntry = new JarEntry(destPath);
            out.putNextEntry(jarEntry);
            in = new FileInputStream(new File(sourcePath));
            copyFile(in, out);

        } finally {
            if (isaBoolean(in)) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                }
            }
        }

        destFile.delete();
    }

    private static boolean isaBoolean(InputStream in) {
        return in != null;
    }

    public static void main(String[] args) {
        String jarPath = "D:\\work\\any-data-hub-parent\\any-data-processor\\src\\main\\resources\\job\\rrrrr1\\dcbdf947400547859929fae8824d18e4\\job-cdc-1.0.0-SNAPSHOT.jar";
        String sourcePath = "D:\\work\\any-data-hub-parent\\any-data-processor\\src\\main\\resources\\job\\rrrrr1\\dcbdf947400547859929fae8824d18e4\\job.json";
        String destPath = "job.json";
        try {
            JarFileReplaceUtil.replaceFile(jarPath, sourcePath, destPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        int length = 2097152;
        byte[] buffer = new byte[length];
        int len = 0;
        while ((len = in.read(buffer)) > -1) {
            out.write(buffer, 0, len);
        }
    }

}