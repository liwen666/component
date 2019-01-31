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

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker测试类
 *
 * @author yzl
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FreemarkerTest {
    public static void main(String[] args) throws IOException, TemplateException {
        Reader reader = new FileReader(new File("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\freemarker\\high\\test.ftl"));
        Template template = new Template("test", reader, null, "utf-8");
        
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("userName", "hello world");
        data.put("list", Arrays.asList("entity1","entity2"));
        data.put("isNall", "no 我不是空");
//        data.put("age", "10");
        Writer writer = new PrintWriter(System.out);
        
        template.process(data, writer);
        
        writer.flush();
        writer.close();
        reader.close();
    }
}