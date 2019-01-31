package com.temp.common.freemarker.high;

import com.temp.common.base.aspectj.User;
import com.temp.common.freemarker.FreemarkerConfig;
import com.temp.common.freemarker.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestConversion {
    public static void main(String[] args) throws IOException, TemplateException {
        Map hashMap=new HashMap();
        hashMap.put("username", "朱绍");
        hashMap.put("user",new User(){{this.setUsername("abc");}});
        hashMap.put("age", "17");
        hashMap.put("listaa", new ArrayList(){{add(new User(){{this.setUsername("abc");}});}});


        Configuration cfg = FreemarkerConfig.getInstance();
        String templateContent="Hi!  ${username}, 我的年龄是：${age}";
        cfg.setTemplateLoader(new StringTemplateLoader(templateContent));
        cfg.setDefaultEncoding("UTF-8");

        Template template =template = cfg.getTemplate("");

        StringWriter writer = new StringWriter();
            template.process(hashMap, writer);
        System.out.println(writer.toString());
        writer.close();

    }
}
