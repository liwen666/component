package org.csource.common;

import com.alibaba.fastjson.JSON;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * author lw
 * date 2019/8/3  15:49
 * discribe 下载和上传文件
 */
public class FileUploadAndDownload extends StorageClient{
    public static void main(String[] args) {
        try {
            args = new String[2];
            args[0]="fastdfs-client.properties";
            args[1]="E:\\workspace\\hurong_fastdfs\\src\\test\\resources\\fdfs_client.conf";
            System.out.println("java.version=" + System.getProperty("java.version"));
            String conf_filename = args[0];
            String local_filename = args[1];
            ClientGlobal.initByProperties(conf_filename);
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);
            long startTime = System.currentTimeMillis();
            String group_name ="group1";
            String remote_filename;
            ServerInfo[] servers;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            String master_filename = "M00/00/00/wKgqh11BuKWAXNG1AAAACRlEc1E586.txt";
            String prefix_name;
            String file_ext_name;
            byte[] file_buff = "test file 新 kfjdlkaj  1111".getBytes();
            NameValuePair[] meta_list = new NameValuePair[4];
            meta_list[0] = new NameValuePair("width", "1024");
            meta_list[1] = new NameValuePair("heigth", "768");
            meta_list[2] = new NameValuePair("bgcolor", "#000000");
            meta_list[3] = new NameValuePair("title", "Untitle");
            prefix_name = "-part1";
            file_ext_name = "txt";
            String[] results  = client.upload_file(file_buff, "txt", meta_list);
//            String[] results = client.upload_file(group_name, master_filename, prefix_name, file_buff, file_ext_name, meta_list);  //创建文件的副本
            System.out.println("上传文件耗时"+(System.currentTimeMillis()-startTime));
            startTime=System.currentTimeMillis();
            System.out.println(JSON.toJSONString(results));
            InetSocketAddress inetSockAddr = trackerServer.getInetSocketAddress();
           String  file_url = "http://" + inetSockAddr.getAddress().getHostAddress()+"/"+group_name+"/"+results[1];
            System.out.println("文件地址：  "+file_url);

         ///下载文件
            String downFileName = results[1];
//            file_buff = client.download_file(group_name, downFileName);
            file_buff = client.download_file("group1", downFileName);
            System.out.println("download_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

            if (file_buff != null) {
                System.out.println("file length:" + file_buff.length);
                System.out.println((new String(file_buff)));
            }
            NameValuePair[] group1s = client.get_metadata("group1", downFileName);
            System.out.println(JSON.toJSONString(group1s));




        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downFile() {
        System.out.println("java.version=" + System.getProperty("java.version"));
        String conf_filename = "fastdfs-client.properties";
        String local_filename ="E:\\workspace\\hurong_fastdfs\\src\\test\\resources\\fdfs_client.conf";
        try {
            ClientGlobal.initByProperties(conf_filename);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            String group_name = "group1";
            String remote_filename="M00/00/01/wKgqh11CJ76ACOn6AAA7dR0Imy49..java";
            byte[] group1s = client.download_file(group_name, remote_filename);
            if (group1s != null) {
                System.out.println("file length:" + group1s.length);
                System.out.println((new String(group1s)));
            }
            int errno;
            errno = client.download_file(group_name, remote_filename, 0, 0, "e:\\" + remote_filename.replaceAll("/", "_"));
            if (errno == 0) {
                System.err.println("Download file success");
            } else {
                System.err.println("Download file fail, error no: " + errno);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upload() {
        System.out.println("java.version=" + System.getProperty("java.version"));
        String conf_filename = "fastdfs-client.properties";
        String local_filename ="E:\\workspace\\hurong_fastdfs\\src\\test\\resources\\fdfs_client.conf";
        try {
            ClientGlobal.initByProperties(conf_filename);
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);
            long startTime = System.currentTimeMillis();
            String group_name ="group1";
            String remote_filename;
            ServerInfo[] servers;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            String master_filename = "M00/00/00/wKgqh11BuKWAXNG1AAAACRlEc1E586.txt";
            String prefix_name;
            String file_ext_name;
            byte[] file_buff = "test file 新 kfjdlkaj  1111".getBytes();
            NameValuePair[] meta_list = new NameValuePair[4];
            meta_list[0] = new NameValuePair("width", "1024");
            meta_list[1] = new NameValuePair("heigth", "768");
            meta_list[2] = new NameValuePair("bgcolor", "#000000");
            meta_list[3] = new NameValuePair("title", "Untitle");
            prefix_name = "-part1";
            file_ext_name = "txt";
            String[] results  = client.upload_file(file_buff, "txt", meta_list);
//            String[] results = client.upload_file(group_name, master_filename, prefix_name, file_buff, file_ext_name, meta_list);  //创建文件的副本
            System.out.println("上传文件耗时"+(System.currentTimeMillis()-startTime));
            startTime=System.currentTimeMillis();
            System.out.println(JSON.toJSONString(results));
            InetSocketAddress inetSockAddr = trackerServer.getInetSocketAddress();
            String  file_url = "http://" + inetSockAddr.getAddress().getHostAddress()+"/"+group_name+"/"+results[1];
            System.out.println("文件地址：  "+file_url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upload2() {
        System.out.println("java.version=" + System.getProperty("java.version"));
        String conf_filename = "fastdfs-client.properties";
        String local_filename ="E:\\workspace\\hurong_fastdfs\\src\\test\\resources\\fdfs_client.conf";
        try {
            ClientGlobal.initByProperties(conf_filename);
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);
            long startTime = System.currentTimeMillis();
            String group_name ="group3";
            ServerInfo[] servers;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer[] storeStorages = tracker.getStoreStorages(trackerServer,group_name);
            System.out.println("========================服务器有"+JSON.toJSONString(storeStorages));
            StorageServer storageServer = null;

//            此处设置storstorage的服务器地址
            StorageClient client = new StorageClient(trackerServer, storeStorages[0]);
            String file_ext_name;
            byte[] file_buff = "test file 新 kfjdlkaj  1111".getBytes();
            NameValuePair[] meta_list = new NameValuePair[4];
            meta_list[0] = new NameValuePair("width", "1024");
            meta_list[1] = new NameValuePair("heigth", "768");
            meta_list[2] = new NameValuePair("bgcolor", "#000000");
            meta_list[3] = new NameValuePair("title", "Untitle");
            file_ext_name = "txt";
            String[] results = client.upload_file(group_name, file_buff, file_ext_name, meta_list);
            System.out.println("上传文件耗时"+(System.currentTimeMillis()-startTime));
            System.out.println(JSON.toJSONString(results));
            InetSocketAddress inetSockAddr = trackerServer.getInetSocketAddress();
            String  file_url = "http://" + inetSockAddr.getAddress().getHostAddress()+"/"+results[0]+"/"+results[1];
            System.out.println("文件地址：  "+file_url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upload3() {
        System.out.println("java.version=" + System.getProperty("java.version"));
        String conf_filename = "fastdfs-client.properties";
        String local_filename ="E:\\workspace\\hurong_fastdfs\\src\\test\\resources\\fdfs_client.conf";
        try {
            ClientGlobal.initByProperties(conf_filename);
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);
            long startTime = System.currentTimeMillis();
            String group_name ="group2";
            String remote_filename;
            ServerInfo[] servers;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            String master_filename = "M00/00/00/wKgqh11BuKWAXNG1AAAACRlEc1E586.txt";
            String prefix_name;
            String file_ext_name;
            byte[] file_buff = "test file 新 kfjdlkaj  1111".getBytes();
            NameValuePair[] meta_list = new NameValuePair[4];
            meta_list[0] = new NameValuePair("width", "1024");
            meta_list[1] = new NameValuePair("heigth", "768");
            meta_list[2] = new NameValuePair("bgcolor", "#000000");
            meta_list[3] = new NameValuePair("title", "Untitle");
            prefix_name = "-part1";
            file_ext_name = "txt";
            String[] results  = upload_file(group_name,"E:\\workspace\\component\\filesystem\\src\\main\\java\\org\\csource\\common\\Base64.java",".java",meta_list);
//            String[] results = client.upload_file(group_name, master_filename, prefix_name, file_buff, file_ext_name, meta_list);  //创建文件的副本
            System.out.println("上传文件耗时"+(System.currentTimeMillis()-startTime));
            startTime=System.currentTimeMillis();
            System.out.println(JSON.toJSONString(results));
            InetSocketAddress inetSockAddr = trackerServer.getInetSocketAddress();
            String  file_url = "http://" + inetSockAddr.getAddress().getHostAddress()+"/"+group_name+"/"+results[1];
            System.out.println("文件地址：  "+file_url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
