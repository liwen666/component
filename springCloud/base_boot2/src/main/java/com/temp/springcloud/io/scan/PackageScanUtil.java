package com.temp.springcloud.io.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class PackageScanUtil {
    public static Logger logger = LoggerFactory.getLogger(PackageScanUtil.class);
    static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public static void main(String[] args) throws IOException {

//        baseSourceScan("com.temp.springcloud.io");
        findCandidateComponents("com.temp.springcloud.io");

    }

    public static void findCandidateComponents(String basePackage) throws IOException {
        String packageSearchPath = "classpath*:" + basePackage.replaceAll("\\.", "/") + "/**/*";
        Resource[] resources = PackageScanUtil.resourcePatternResolver.getResources(packageSearchPath);
    }

    public static void baseSourceScan(String basePackage) throws IOException {
        String packageSearchPath = "classpath*:" + basePackage.replaceAll("\\.", "/") + "/**/*";
        ClassLoader classLoader = PackageScanUtil.class.getClassLoader();
        Enumeration<URL> resources = classLoader.getResources("com/temp/springcloud/io");
//        Enumeration<URL> resources = classLoader.getResources("classpath*:com.temp.springcloud.io");
        Set<Resource> resourceSet = new LinkedHashSet<Resource>();
        while (resources.hasMoreElements()) {
            URL url1 = resources.nextElement();
            resourceSet.add(new UrlResource(url1));
        }
        for (Resource r : resourceSet) {
            System.out.println(r.getFilename());
        }


    }




}
