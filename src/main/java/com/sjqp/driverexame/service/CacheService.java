package com.sjqp.driverexame.service;

import java.util.Set;

/**
 * @author qinpeng on 2018/11/30.
 */
public interface CacheService {

    /**
     * 加载缓存，返回缓存key
     * @return
     */
    void reloadCache();

    /**
     * 返回缓存对应的数据库表名
     * @return
     */
    String getTableName();
}
