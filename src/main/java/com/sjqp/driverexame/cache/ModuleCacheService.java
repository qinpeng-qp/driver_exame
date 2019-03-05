package com.sjqp.driverexame.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.sjqp.driverexame.entity.Module;
import com.sjqp.driverexame.inc.CacheKey;
import com.sjqp.driverexame.mapper.ModuleMapper;
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
public class ModuleCacheService implements CacheService {
    private Logger logger = LoggerFactory.getLogger(ModuleCacheService.class);

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void reloadCache() {
        List<Module> moduleList = moduleMapper.selectAll();
        Set<String> keys = Sets.newHashSet();
        if (!CollectionUtils.isEmpty(moduleList)){
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(CacheKey.MODULE, JSON.toJSONString(moduleList));
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
            logger.info(CacheKey.MODULE);
        }
    }

    @Override
    public String getTableName() {
        return "module";
    }
}
