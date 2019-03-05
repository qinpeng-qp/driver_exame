package com.sjqp.driverexame.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.sjqp.driverexame.entity.Role;
import com.sjqp.driverexame.inc.CacheKey;
import com.sjqp.driverexame.mapper.RoleMapper;
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
public class RoleCacheService implements CacheService {
    private Logger logger = LoggerFactory.getLogger(RoleCacheService.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void reloadCache() {
        List<Role> roleList = roleMapper.selectAll();
        Set<String> keys = Sets.newHashSet();
        if (CollectionUtils.isEmpty(roleList)){
            Jedis jedis = jedisPool.getResource();
            try {
                for (Role role : roleList) {
                    String roleKey = String.format(CacheKey.ROLE_ROLE_ID,String.valueOf(role.getId()));
                    jedis.set(roleKey, JSON.toJSONString(role));
                    keys.add(roleKey);
                    logger.info(roleKey);
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }

        }

    }

    @Override
    public String getTableName() {
        return "role";
    }
}
