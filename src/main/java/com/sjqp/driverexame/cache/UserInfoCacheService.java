package com.sjqp.driverexame.cache;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Sets;
import com.sjqp.driverexame.entity.UserInfo;
import com.sjqp.driverexame.inc.CacheKey;
import com.sjqp.driverexame.mapper.UserInfoMapper;
import com.sjqp.driverexame.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author qinpeng on 2019/1/10
 */
@Component
public class UserInfoCacheService implements CacheService {
    private Logger logger = LoggerFactory.getLogger(UserInfoCacheService.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void reloadCache() {
        List<UserInfo> userInfoList = userInfoMapper.selectAll();
        if (!CollectionUtils.isEmpty(userInfoList)){
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(CacheKey.USER_INFO, JSON.toJSONString(userInfoList));
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
            logger.info(CacheKey.USER_INFO);
        }

    }

    @Override
    public String getTableName() {
        return "user_info";
    }
}
