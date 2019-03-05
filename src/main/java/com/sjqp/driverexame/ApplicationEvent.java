package com.sjqp.driverexame;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;

import com.sjqp.driverexame.inc.SqlFilter;
import com.sjqp.driverexame.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author qinpeng on 2018/11/30.
 */
@Component
public class ApplicationEvent {

    private Logger logger = LoggerFactory.getLogger(ApplicationEvent.class);

   @Autowired
   private List<CacheService> cacheServices;


    @Autowired
    private DruidDataSource druidDataSource;

    @Autowired
    private SqlFilter sqlFilter;

    @PostConstruct
    public void startup(){
        druidDataSource.setProxyFilters(Lists.newArrayList(sqlFilter));
        for (CacheService cacheService : cacheServices){
            cacheService.reloadCache();
        }
        logger.info("load cache finished");
    }

    @PreDestroy
    public void destory(){
        logger.info("application destory");
    }

}
