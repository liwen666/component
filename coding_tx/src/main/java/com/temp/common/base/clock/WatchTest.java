package com.temp.common.base.clock;

import org.springframework.util.StopWatch;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class WatchTest {
    public static void main(String[] args) throws InterruptedException {
        StopWatch clock = new StopWatch();
        clock.start("test");
        Thread.sleep(20);
        clock.stop();
        System.out.println(clock.prettyPrint());
    }
}
