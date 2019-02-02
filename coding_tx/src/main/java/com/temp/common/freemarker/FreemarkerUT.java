package com.temp.common.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

public class FreemarkerUT {

    /**
     * 在content中查询key，用value替换。返回替换后的整体字符串。
     */
    public static String fillTemplate(String templateContent, Map<String, String> args) {

	String out = "";

	try {
	    Configuration cfg = FreemarkerConfig.getInstance();

	    cfg.setTemplateLoader(new StringTemplateLoader(templateContent));
	    cfg.setDefaultEncoding("UTF-8");

	    Template template = cfg.getTemplate("");

	    StringWriter writer = new StringWriter();
	    template.process(args, writer);
	    System.out.println(writer.toString());

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return out;

    }

}
