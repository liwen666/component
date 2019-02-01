package com.temp.common.freemarker.high;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker测试类
 *
 * @author yzl
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FreemarkerTestAll {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        File files = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\freemarker\\high");
        cfg.setDirectoryForTemplateLoading(files);
        Template template = cfg.getTemplate("test.ftl");
        Template template2 = cfg.getTemplate("head.html.ftl");

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("userName", "hello world");
        data.put("list", Arrays.asList("entity1", "entity2"));
        data.put("isNall", "no 我不是空");
//        data.put("age", "10");
        Writer writer = new PrintWriter(System.out);

        template.process(data, writer);
        System.out.println("---------------------------------------------------");
        template2.process(data, writer);
        writer.flush();
        writer.close();
    }
    static  class TestInclub{
        public static void main(String[] args) throws IOException, TemplateException {
            Configuration cfg = new Configuration();
            File files = new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\freemarker\\high");
            cfg.setDirectoryForTemplateLoading(files);
            Template template = cfg.getTemplate("test.ftl");

            Map<Object, Object> data = new HashMap<Object, Object>();
            data.put("userName", "hello world");
            data.put("list", Arrays.asList("entity1", "entity2"));
            data.put("isNall", "no 我不是空");
//        data.put("age", "10");
            Writer writer = new PrintWriter(System.out);

            template.process(data, writer);
            System.out.println("---------------------------------------------------");
            writer.flush();
            writer.close();
        }
    }
}