package com.temp.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PackageScanUtils {
	public static Map<String, Map<String, Resource>> resourceStatics = new ConcurrentHashMap<>();
	public static Logger logger = LoggerFactory.getLogger(PackageScanUtils.class);

	public static void findResourceByPath(String filePath, List<File> resource){
		File file = new File(filePath);
		if(file.exists()){
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				logger.info("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						findResourceByPath(file2.getAbsolutePath(),resource);
					} else {
						resource.add(file2);
					}
				}
			}
		}else{
			logger.info("路径不存在!");
		}
	}

	public static Resource findResource(String packagePatten, String fileName, boolean cache) throws IOException {
		if (cache) {
			if (null == resourceStatics.get(packagePatten)) {
				initResource(packagePatten);
			}
			Resource resource = resourceStatics.get(packagePatten).get(fileName);
			return resource;
		}else{
			initResource(packagePatten);
			Resource resource = resourceStatics.get(packagePatten).get(fileName);
			return resource;
		}

	}

	public static void initResource(String packagePatten) throws IOException {

		String packageSearchPath = "classpath*:" + packagePatten.replaceAll("\\.", "/") + "/**/*";
		Map<String, Resource> resourceMap = new HashMap<>();
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
		for (Resource resource : resources) {
//			if (null != resourceMap.get(resource.getFilename())) {
//				logger.error("初始化资源有重复" + resource.getURL());
//			}
			resourceMap.put(resource.getFilename(), resource);
		}
		resourceStatics.put(packagePatten, resourceMap);
	}

}
