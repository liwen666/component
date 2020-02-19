package com.temp.common.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PackageScanUtil1 {
    public static Map<String, Map<String, Resource>> resourceStatics = new ConcurrentHashMap<>();
public  static Logger logger = LoggerFactory.getLogger(PackageScanUtil1.class);
    public static void main(String[] args) throws IOException {

        Resource resource = findResource("static.map", "china.json");

    }


    public static Resource findResource(String packageParten, String fileName) throws IOException {

        if(null==resourceStatics.get(packageParten)){
            initResource(packageParten);
        }
        Resource resource = resourceStatics.get(packageParten).get(fileName);
        return resource;
    }

    public static void initResource(String packageParten) throws IOException {

        String packageSearchPath = "classpath*:" + packageParten.replaceAll("\\.", "/") + "/**/*";
        Map<String, Resource> resourceMap = new HashMap<>();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        for (Resource resource : resources) {
            if( null!=resourceMap.get(resource.getFilename())){
                logger.error("初始化资源有重复"+resource.getURL());
            };
            resourceMap.put(resource.getFilename(),resource);
        }
        resourceStatics.put(packageParten,resourceMap);
    }

}
