package com.temp.common.base.thread.future;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        MsgFuture m = new MsgFuture(1010,System.currentTimeMillis());
        Thread t = new Thread(m);
        t.start();
        try {
            Object o = m.get();
            System.out.println(o.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
