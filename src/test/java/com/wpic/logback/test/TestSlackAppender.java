package com.wpic.logback.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlackAppender {

    private static final Logger logger = LoggerFactory.getLogger("TestSlackAppender");

    @Test
    public void test() {
        logger.info("Hello slack!");

        try {
            final int x = 1/0;
        } catch (Exception ex) {
            logger.info("Some error", ex);
        }
    }

}
