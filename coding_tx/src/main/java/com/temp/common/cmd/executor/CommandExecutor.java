package com.temp.common.cmd.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
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
 * @since 2021/4/2  11:33
 */
@Service
@Slf4j
public class CommandExecutor implements ICommandLine {
    private static ThreadPoolExecutor executor;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("job-exec-pool-%d").build();
        //根据实际情况创建线程池
        executor = new ThreadPoolExecutor(10, 2000, 5,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public String buildJarCommand(CommandBuild commandBuild, List<String> params) {
        return commandBuild.build(params);
    }

    @Override
    public String buildCommand(CommandBuild commandBuild, AppType appType, List<String> args) {
        return commandBuild.build(appType, args);
    }

    @Override
    public void exec(String command, String charSet) {
        log.info("调用系统命令：{}", command);
        Runtime run = Runtime.getRuntime();
        Process p = null;
        InputStream stream = null;
        try {
            p = run.exec(command);
            stream = p.getInputStream();
            //消费正常日志
            clearStream(stream, charSet);
            //消费错误日志
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {
                    log.error("=============exec=====================命令执行失败! command:{}", command);
                }
            }

        } catch (Exception e) {
            log.error("=============exec=====================命令执行失败!error:{} command:{}", e, command);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
            if (p != null) {
                p.destroy();
            }
        }
    }

    private void clearStream(InputStream stream, String charSet) {
        //处理buffer的线程
        executor.execute(() -> {
            String line;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(stream, charSet == null ? "utf-8" : charSet))) {
                while ((line = in.readLine()) != null) {
                    log.info(line);
                }
            } catch (IOException e) {
                log.error("=============exec=====================命令执行失败! error:{}", e);
            }
        });
    }
}

