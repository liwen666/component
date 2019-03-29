package com.temp.common.base.innerclass.demo3;

public class Example3 extends MyIncrement {

    private int i = 0;

    private void incr() {

        i++;

        System.out.println(i+"实现Incrementable  接口方法调用");


    }

    private class Closure implements Incrementable {

        public void increment() {

            incr();

        }

    }

   public Incrementable getCallbackReference() {

        return new Closure();
    }

}