package com.temp.common.base.thread.pooldesign.demo;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MySemaphore {
 
    public static void main(String[] args) throws IOException, InterruptedException {
        final File stream = new File("c:\\temp\\stonefeng\\stream.txt");
        final OutputStream os = new FileOutputStream(stream);
        final OutputStreamWriter writer = new OutputStreamWriter(os);
        final Semaphore semaphore = new Semaphore(10);
        ExecutorService exec = Executors.newCachedThreadPool();
        
        final long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            final int num = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        writer.write(String.valueOf(num)+"\n");
                        semaphore.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.submit(task);
        }
        exec.shutdown();
        while(true){  
           if(exec.isTerminated()){  
                writer.write("---END---\n");
                writer.close();
                System.out.println("所有的子线程都结束了！");  
                break;  
            }  
            Thread.sleep(1000);    
        }
        final long end = System.currentTimeMillis();
        System.out.println((end-start)/1000);
    }
}