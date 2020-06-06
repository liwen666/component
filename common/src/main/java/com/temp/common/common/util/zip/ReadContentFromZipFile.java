package com.temp.common.common.util.zip;


import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @ClassName: ReadContentFromZipFile
 * @Description: Reading content of a ZIP file means that you list all entries
 * contained in the file without extracting them.
 * <p>
 * <p>
 * In the java.util.zip package, the ZipFile class can be used to
 * open and read a ZIP file like this:
 * <p>
 * ZipFile zipFile = new ZipFile(zipFilePath); Enumeration<?
 * extends ZipEntry> entries = zipFile.entries();
 * <p>
 * <p>
 * Each entry is of type ZipEntry which provides the following
 * methods for reading information of an individual entry (to name
 * a few):
 * <p>
 * getName(): returns the name of the entry in form of a relative
 * path
 * <p>
 * getComment(): returns comment of the entry getCompressedSize():
 * returns the compressed size of the entry in bytes.
 * <p>
 * getSize(): returns the normal size (uncompressed) of the entry
 * in bytes.
 * <p>
 * isDirectory(): tells whether the entry is a directory or not.
 * @author: Mr.Yang
 * @date: 2017年9月7日 下午8:38:59
 */
public class ReadContentFromZipFile {
    /**
     * @param zipFilePath
     * @Title: readContentFromZipFile
     * @Description: TODO
     * @return: void
     */
    public static void readContentFromZipFile(String zipFilePath) {

        try {
            ZipFile zipFile = new ZipFile(zipFilePath);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                long compressedSize = entry.getCompressedSize();
                long normalSize = entry.getSize();
                String type = entry.isDirectory() ? "DIR" : "FILE";

                System.out.println(name);
                System.out.format("\t %s - %d - %d\n", type, compressedSize, normalSize);
            }

            zipFile.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        ReadContentFromZipFile.readContentFromZipFile("D:\\idea2018workspace\\component_new\\common\\src\\main\\java\\com\\temp\\common\\common\\util\\aaa.zip");
    }



}