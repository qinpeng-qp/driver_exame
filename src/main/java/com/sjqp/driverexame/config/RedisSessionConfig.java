package com.sjqp.driverexame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author jiashiran on 2019/1/8.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 900)
public class RedisSessionConfig {
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
