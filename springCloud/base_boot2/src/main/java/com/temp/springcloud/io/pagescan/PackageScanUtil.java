package com.temp.springcloud.io.pagescan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashSet;

public class PackageScanUtil {
    public static Logger logger = LoggerFactory.getLogger(PackageScanUtil.class);
    static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public static void main(String[] args) throws IOException {

//        findCandidateComponents("com");
        findResource("com.temp.springcloud.io");

    }

    public static void findCandidateComponents(String basePackage) throws IOException {
        LinkedHashSet candidates = new LinkedHashSet();

//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*";
        String packageSearchPath = "classpath*:com/temp/springcloud/sqlscript/**/*";
//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*.class";
        Resource[] resources = PackageScanUtil.resourcePatternResolver.getResources(packageSearchPath);
        boolean traceEnabled = PackageScanUtil.logger.isTraceEnabled();
        boolean debugEnabled = PackageScanUtil.logger.isDebugEnabled();
        Resource[] arr$ = resources;
        int len$ = resources.length;
        for (Resource resource : resources) {
            File file = resource.getFile();
            System.out.println("是否是文件" + file.isFile());
            System.out.println(resource.getFilename());
            System.out.println(resource.getURI());
            System.out.println(resource.getURL());
            System.out.println(resource.isFile());
        }


    }

    public static Resource[] findResource(String packageParten) throws IOException {
        LinkedHashSet candidates = new LinkedHashSet();

//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*";
//        String packageSearchPath = "classpath*:com/temp/springcloud/sqlscript/**/*";
        String packageSearchPath = "classpath*:" + packageParten.replaceAll("\\.", "/") + "/**/*";
//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*.class";
//        Resource[] resources = PackageScanUtil.resourcePatternResolver.getResources(packageSearchPath);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        for (Resource r : resources) {
            System.out.println(r.getURI());
            System.out.println(r.getURI().toString());
            System.out.println(r.getFilename());

        }
        System.out.println("-----------------自定义文件查询---------------------");
        URL resource = PackageScanUtil.class.getClassLoader().getResource("com/temp/springcloud/sqlscript");
//        Resource r = new UrlResource(resource);
        File file = null;
        try {
            file = new File(resource.toURI().getSchemeSpecificPart());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        showFile(file);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
//        boolean flag = true;
//        System.out.println(file.getName());
//        if (file.isDirectory()) {
//            File[] files1 = file.listFiles();
//            for(File f:files1){
//                System.out.println(f.getName());
//                if(f.isDirectory()){
//                    File[] files = f.listFiles();
//                }
//            }
//
//        }

        System.out.println("-----------------自定义文件查询---------------------");
        return resources;
    }

    private static void showFile(File file) {
        logger.info(file.getName());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f: files){
                if(f.isDirectory()){
                    showFile(f);
                }else {
                    logger.info(f.getName());
                }
            }
        }
    }
}
