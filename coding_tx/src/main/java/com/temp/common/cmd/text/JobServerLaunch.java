package com.temp.common.cmd.text;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class JobServerLaunch {
    @Test
    public void launchJobServer() throws IOException {

        String property = System.getProperty("os.name");
        log.info("当前操作系统是:{}", property);

        // 简单执行一条命令，可以看到当前目录为classpath
        Process process = Runtime.getRuntime().exec("ping localhost");
        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // 执行带有环境变量的exec，环境变量在命令执行前被设置
//        String[] envp = {"a=yj"};
//        Process process1 = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo $a"}, envp);
//        InputStreamReader inputStreamReader1 = new InputStreamReader(process1.getInputStream());
//        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
//
//        String line1 = null;
//        while ((line1 = bufferedReader1.readLine()) != null) {
//            System.out.println(line1);
//        }

        // 改变当前目录执行shell脚本，环境变量在脚本执行前被设置
//        Process process2 = Runtime.getRuntime().exec("./hello.sh", new String[]{"a=cfh"}, new File("/Users/chenfeihao/Desktop"));
//        InputStreamReader inputStreamReader2 = new InputStreamReader(process2.getInputStream());
//        BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);
//
//        String line2 = null;
//        while ((line2 = bufferedReader2.readLine()) != null) {
//            System.out.println(line2);
//        }

    }

}