package com.temp.common.complier.jarhome;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.Assert.*;

public class JarReadTest {

    @Test
    public void nam1e() throws ClassNotFoundException, NoSuchMethodException {
        name("D:\\workspace\\strategy-topology\\job-cdc\\target");
        Class<?> aClass = Class.forName("com.riveretech.est.cdc.JobCdcApp");
    }


    @Test
    public void nam12() throws ClassNotFoundException, NoSuchMethodException {
        name("D:\\workspace\\strategy-topology\\common\\target");
        Class<?> aClass = Class.forName("com.riveretech.est.common.utils.ResourceUtils");
        System.out.println(aClass.getName());
    }

    public void name(String path) throws NoSuchMethodException, ClassNotFoundException {
        // 系统类库路径
        File libPath = new File(path);

// 获取所有的.jar和.zip文件
        File[] jarFiles = libPath.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar") || name.endsWith(".zip");
            }
        });

        if (jarFiles != null) {
            // 从URLClassLoader类中获取类所在文件夹的方法
            // 对于jar文件，可以理解为一个存放class文件的文件夹
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            boolean accessible = method.isAccessible();		// 获取方法的访问权限
            try {
                if (accessible == false) {
                    method.setAccessible(true);		// 设置方法的访问权限
                }
                // 获取系统类加载器
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                for (File file : jarFiles) {
                    URL url = file.toURI().toURL();
                    try {
                        method.invoke(classLoader, url);
                        System.out.println("读取jar文件[name={}]"+ file.getName());
                    } catch (Exception e) {
                        System.out.println("读取jar文件失败 [name={}]"+ file.getName());
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                method.setAccessible(accessible);
            }
        }
    }
}