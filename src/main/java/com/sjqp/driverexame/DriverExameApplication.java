package com.sjqp.driverexame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qinpeng
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan
public class DriverExameApplication extends SpringBootServletInitializer {
    static Logger logger = LoggerFactory.getLogger(DriverExameApplication.class);

    /**
     *重写 configure方法
      */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DriverExameApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DriverExameApplication.class, args);
        logger.info("start ");
    }
}
