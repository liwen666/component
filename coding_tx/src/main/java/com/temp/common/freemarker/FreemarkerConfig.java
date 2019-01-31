package com.temp.common.freemarker;

import freemarker.template.Configuration;

public class FreemarkerConfig {

    // 取得一个Template的示例

    private FreemarkerConfig() {

    }

    public static Configuration getInstance() {
	return SingletonHolder.instance;
    }
    
    private static class SingletonHolder {
	/**
	 * 静态初始化器，由JVM来保证线程安全
	 */
	private static Configuration instance = new Configuration();

    }

}
