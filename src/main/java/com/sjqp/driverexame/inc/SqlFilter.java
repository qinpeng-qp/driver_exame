package com.sjqp.driverexame.inc;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.sjqp.driverexame.service.CacheService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * @author qinpeng on 2018/12/4.
 */
@Component
public class SqlFilter extends FilterEventAdapter {

    private Logger logger = LoggerFactory.getLogger(SqlFilter.class);

    @Autowired
    private List<CacheService> cacheServiceList;


    /**
     * 根据sql执行结果判定是否更新缓存，做pub广播
     * @param statement
     * @param sql
     * @param result
     */
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        //查询返回true，更新或插入返回false
        if(result == false){
            sql = sql.toLowerCase();
            if((sql.contains("insert") || sql.contains("update") ||  sql.contains("delete")) && !sql.contains("select")){
                for (CacheService cacheService : cacheServiceList){
                    if(sql.contains(cacheService.getTableName())){
                        logger.info("sql:{}",sql);
                        logger.info("table:{}",cacheService.getTableName());
                        cacheService.reloadCache();
                    }
                }
                logger.info(sql);
            }
        }
        super.statementExecuteAfter(statement, sql, result);

    }

}
