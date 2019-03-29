package com.temp.common.base.innerclass.demo1;

public class Example {
    private class InsideClass implements InterfaceTest {
        protected void test() {
            System.out.println("父类  外部内内部内的方法调用");
            Example.this.protectMd(this.getClass().getName());
        }
        @Override
        public void increment() {
            System.out.println("这是一个测试");
        }
    }
    public  void test(){
        new InsideClass().test();
    }
    public InterfaceTest getIn() {
        return new InsideClass();
    }
    protected  void protectMd(String className){
        System.out.println("外部内受保护方法"+className);
    }
}
