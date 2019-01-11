package com.temp.springcloud.io.pagescan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
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
        String packageSearchPath = "classpath*:"+packageParten.replaceAll("\\.","/")+"/**/*";
//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*.class";
        Resource[] resources = PackageScanUtil.resourcePatternResolver.getResources(packageSearchPath);
        for (Resource r:resources){
            System.out.println(r.getURI());
            System.out.println(r.getFilename());

        }
        return resources;
    }
}
