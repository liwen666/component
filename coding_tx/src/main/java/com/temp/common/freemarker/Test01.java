package com.temp.common.freemarker;

import java.util.HashMap;
import java.util.Map;

public class Test01 {

    public static void main(String[] args) {

	Test01 t = new Test01();
	t.test01();

    }

    @SuppressWarnings("unchecked")
    private void test01() {

	Map args=new HashMap();
	args.put("username", "朱绍");
	args.put("age", "17");
	
	String templateContent="Hi! ${username}, 我的年龄是：${age}";
	String out=FreemarkerUT.fillTemplate(templateContent, args);
	
	System.out.println(out);
    }
}
