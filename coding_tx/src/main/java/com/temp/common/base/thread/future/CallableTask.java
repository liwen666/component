package com.temp.common.base.thread.future;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("执行成功");
        Thread.sleep(3000);
        return "任务完成";
    }
}
