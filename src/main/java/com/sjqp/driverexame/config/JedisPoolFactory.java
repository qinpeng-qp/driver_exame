package com.sjqp.driverexame.config;


import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author qinpeng on 2018/11/26.
 */
//@Configuration
public class JedisPoolFactory {


    public JedisPool pool;

   // @Bean
    public JedisPool getJedisPool(RedisProperties redisProperties) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        config.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        config.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        if(StringUtils.isEmpty(redisProperties.getPassword())){
            redisProperties.setPassword(null);
        }
        int timeout = 2000;
        if(redisProperties.getTimeout() != null && redisProperties.getTimeout().getSeconds() > 0){
            timeout = (int)redisProperties.getTimeout().getSeconds() * 1000;
        }
        pool = new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(),
                timeout,redisProperties.getPassword(),redisProperties.getDatabase());

        return pool;
    }

}
