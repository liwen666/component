package com.temp.common.cmd;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/25  11:56
 */
public class CommdUtils {


    private static ThreadPoolExecutor executor;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("cmd-pool-%d").build();
        //根据实际情况创建线程池
        executor = new ThreadPoolExecutor(6, 10, 5,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 流处理
     * @param stream
     */
    private static void clearStream(InputStream stream) {
        //处理buffer的线程
        executor.execute(new Runnable() {
            @Override
            public void run() {

                String line = null;

                try (BufferedReader in = new BufferedReader(new InputStreamReader(stream,"gbk"));) {
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean execCommond(String... args) {
        boolean flg = true;
        Runtime run = Runtime.getRuntime();
        try {
            Process p;
            if (args != null && args.length == 1) {
                p = run.exec(args[0]);
            } else {
                p = run.exec(args);
            }

            InputStream stream=p.getInputStream();
            System.out.println( stream + "....getInputStream..");

            //消费正常日志
            clearStream(stream);
            //消费错误日志
            clearStream(p.getErrorStream());

            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {
                    System.out.println("=============exec=====================命令执行失败!");
                    flg = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            flg = false;
        }
        return flg;
    }

    public static void main(String[] args) {
//        CommdUtils.execCommond("ping localhost");
        CommdUtils.execCommond("ipconfig /" +
                "all");
        executor.shutdown();

    }

}
