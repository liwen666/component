package com.temp.common.base.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggerTest {
    private  static Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    public static void main(String[] args) {
        String path = LoggerTest.class.getResource("").getPath();
        String config = path.substring(1,path.length())+"logback.xml";
        System.out.println(config);

        File logbackFile = new File(config);
        if (logbackFile.exists()) {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            try {
                configurator.doConfigure(logbackFile);
            }
            catch (JoranException e) {
                e.printStackTrace(System.err);
                System.exit(-1);
            }
        }

        logger.debug("debug===============");
        logger.info("info ============");
        logger.warn("warn===============");
        logger.error("error===============");
    }
}
