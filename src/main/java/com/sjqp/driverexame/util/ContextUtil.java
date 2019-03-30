package com.sjqp.driverexame.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 获取spring的ApplicationContext。通过ApplicationContext可以获取在spring配置文件中配置的类。
 * <p>
 */
@Component
public class ContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> type, Object... args) {
        return applicationContext.getBean(type, args);
    }


    @PostConstruct
    public void init() {

    }


}
