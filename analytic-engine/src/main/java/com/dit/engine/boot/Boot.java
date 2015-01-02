package com.dit.engine.boot;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Boot {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Boot.class);

    public static void main(String[] args) {
        logger.info("================ Starting Analytic Engine ==========");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("engine-context.xml");
        logger.info("================ Started ===========================");

    }
}
