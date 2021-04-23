package com.temp.common.complier.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <p>
 * 描述扩展依赖
 * </p>
 *
 * @author LW
 * @since 2021/3/29  17:27
 */
@Slf4j
public class DynamicClassLoad {
    public static void load(String[] args) throws NoSuchMethodException {
        ParameterTool parameters = ParameterTool.fromArgs(args);
        String lib = parameters.has(Constants.DEPENDENCY_EXTERNAL) ? parameters.get(Constants.DEPENDENCY_EXTERNAL) : null;
        if (null == lib) {
            return;
        } else {
            System.out.println("外部依赖地址是：" + lib);

        }
        File libPath = new File(lib);
        File[] jarFiles = libPath.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".zip"));

        if (jarFiles != null) {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            boolean accessible = method.isAccessible();
            try {
                if (accessible == false) {
                    method.setAccessible(true);
                }
                // 获取系统类加载器
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                for (File file : jarFiles) {
                    URL url = file.toURI().toURL();
                    try {
                        method.invoke(classLoader, url);
                        System.out.println("读取jar文件[name={}]" + file.getName());
                    } catch (Exception e) {
                        System.out.println("读取jar文件失败 [name={}]" + file.getName());
                    }
                }
            } catch (MalformedURLException e) {
                System.out.println("读取jar文件失败");
            } finally {
                method.setAccessible(accessible);
            }
        }
    }
}
