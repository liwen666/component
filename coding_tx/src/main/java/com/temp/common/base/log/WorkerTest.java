package com.temp.common.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerTest extends Thread {
    Logger logger = LoggerFactory.getLogger(WorkerTest.class);
    @Override
    public void run() {
        logger.debug("   other Thread log info ...");
        super.run();
    }
}
