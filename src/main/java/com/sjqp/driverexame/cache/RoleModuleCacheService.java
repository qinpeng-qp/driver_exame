package com.sjqp.driverexame.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;

import com.sjqp.driverexame.entity.RoleModule;
import com.sjqp.driverexame.inc.CacheKey;
import com.sjqp.driverexame.mapper.RoleModuleMapper;
import com.sjqp.driverexame.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * @author qinpeng on 2019/1/10
 */
@Component
public class RoleModuleCacheService implements CacheService {
    private Logger logger = LoggerFactory.getLogger(RoleModuleCacheService.class);

    @Autowired
    private RoleModuleMapper roleModuleMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void reloadCache() {
        List<RoleModule> roleModuleList = roleModuleMapper.selectAll();
        Set<String> keys = Sets.newHashSet();
        if (!CollectionUtils.isEmpty(roleModuleList)){
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(CacheKey.ROLE_MODULE, JSON.toJSONString(roleModuleList));
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
            logger.info(CacheKey.ROLE_MODULE);
        }

    }

    @Override
    public String getTableName() {
        return "role_module";
    }
}
