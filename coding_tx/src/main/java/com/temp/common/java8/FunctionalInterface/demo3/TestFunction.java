package com.temp.common.java8.FunctionalInterface.demo3;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/26  15:29
 */
public class TestFunction {
    public static void main(String[] args) {
        FunctionTest1 functionTest1 = new FunctionTest1();
        functionTest1.createFunction("abc");
        String cs = functionTest1.getName("测试");
        System.out.println(cs);
    }
}

class FunctionTest1 {
    private IDemoFunction demoFunction;
    private String name;

    public void createFunction(String str) {
        System.out.println(str);
        demoFunction = (abc, bbb) ->abc+ str + "bbb";
    }


    public String getName(String bbb) {
         return (String) demoFunction.exec(bbb, null);
    }

    public void setName(String name) {
        this.name = name;
    }
}