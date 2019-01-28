package com.temp.common.base.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class FileCopy {
    public static void main(String[] args) {
        URL resource = FileCopy.class.getResource("");
        String s = "D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\io";
        String target = "D:\\component\\component\\coding_tx\\src\\main/target";
        File f = new File(s);
        File d = new File(target);
        //删除文件
//        delFile(f);
        //复制文件
        fileCopy(s, target);
//        fileCopy_channel("D:\\component\\component\\springCloud\\base_boot2\\src\\main\\iotest", "D:\\component\\component\\springCloud\\base_boot2\\src\\main\\iotarget");
    }

    public static void fileCopy(String fileName, String targetName) {
        FileInputStream input = null;
        FileOutputStream output = null;

        try {
            File file = new File(fileName);
            if (file.isDirectory()) {
                File targetDir = new File(targetName);
                targetDir.mkdirs();
                String[] list = file.list();
                System.out.println(Arrays.toString(list));
                for (String f : list) {
                    fileCopy(fileName + "/" + f, targetName + "/" + f);
                }
            } else {
                input = new FileInputStream(file);
                File fileTarget = new File(targetName);
                output = new FileOutputStream(fileTarget);

                byte[] bt = new byte[1024];
                int realbyte = 0;
                /**      input.read(bt)
                 * Reads up to <code>b.length</code> bytes of data from this input
                 * stream into an array of bytes. This method blocks until some input
                 * is available.
                 *
                 * @param      b   the buffer into which the data is read.
                 * @return the total number of bytes read into the buffer, or
                 *             <code>-1</code> if there is no more data because the end of
                 *             the file has been reached.
                 * @exception IOException  if an I/O error occurs.
                 */
                while ((realbyte = input.read(bt)) > 0) {

                    /**   output.write(bt,0,realbyte)
                     * Writes <code>len</code> bytes from the specified byte array
                     * starting at offset <code>off</code> to this file output stream.
                     *
                     * @param      b     the data.
                     * @param      off   the start offset in the data.
                     * @param      len   the number of bytes to write.
                     * @exception IOException  if an I/O error occurs.
                     */
                    output.write(bt, 0, realbyte);
                }

                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileCopy_channel(String fileName, String targetName) {
        FileChannel input = null;
        FileChannel output = null;

        try {
            File file = new File(fileName);
            if (file.isDirectory()) {
                File targetDir = new File(targetName);
                targetDir.mkdirs();
                String[] list = file.list();
                for (String f : list) {
                    fileCopy_channel(fileName + "/" + f, targetName + "/" + f);
                }
            } else {
            input = new FileInputStream(file).getChannel();
            output = new FileOutputStream(targetName).getChannel();
            /**
             * Transfers bytes into this channel's file from the given readable byte channel.
             *  @param  src
                 *         The source channel
                 *
                 * @param  position
                 *         The position within the file at which the transfer is to begin;
                 *         must be non-negative
                 *
                 * @param  count
                 *         The maximum number of bytes to be transferred; must be
                 *         non-negative
             */
            output.transferFrom(input, 0, input.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //        删除文件
    static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

}

