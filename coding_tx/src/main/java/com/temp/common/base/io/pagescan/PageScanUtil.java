package com.temp.common.base.io.pagescan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

public class PageScanUtil {
    public static Logger logger = LoggerFactory.getLogger(PageScanUtil.class);
    static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    public static void main(String[] args) throws IOException {

//        findCandidateComponents("com");
        findInputStream("com.temp.common.websocket.example","keystore.jks");
    }
    public static void findCandidateComponents(String basePackage) throws IOException {
        LinkedHashSet candidates = new LinkedHashSet();

//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*";
            String packageSearchPath = "classpath*:com/temp/springcloud/sqlscript/**/*";
//            String packageSearchPath = "classpath*:com/temp/springcloud/io/**/*.class";
//            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
            Resource[] resources = PageScanUtil.resourcePatternResolver.getResources(packageSearchPath);
            boolean traceEnabled = PageScanUtil.logger.isTraceEnabled();
            boolean debugEnabled = PageScanUtil.logger.isDebugEnabled();
            Resource[] arr$ = resources;
            int len$ = resources.length;
            for(Resource resource:resources){
                File file = resource.getFile();
                System.out.println("是否是文件"+file.isFile());
                System.out.println(resource.getFilename());
                System.out.println(resource.getURI());
                System.out.println(resource.getURL());
            }


    }
   public static InputStream findInputStream(String relativePackage,String fileName) throws IOException {
       String packageSearchPath = "classpath*:"+relativePackage.replaceAll("\\.","/")+"/**/*";
       Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
       for(Resource resource:resources){
           if(resource.getFilename().equals(fileName)){
               logger.info("查找到资源：{}",fileName);
               return resource.getInputStream();
           }
       }
        return null;
    }
}
