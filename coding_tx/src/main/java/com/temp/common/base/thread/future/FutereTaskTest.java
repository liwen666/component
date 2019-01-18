package com.temp.common.base.thread.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutereTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> ft = new FutureTask<String>(new CallableTask());
        System.out.println(ft.isDone());;
        Thread t = new Thread(ft);
        t.start();
        System.out.println(ft.isDone());
        System.out.println("主线程继续");
        String s = ft.get();
        System.out.println(ft.isDone());;
        System.out.println(s);
    }
}
