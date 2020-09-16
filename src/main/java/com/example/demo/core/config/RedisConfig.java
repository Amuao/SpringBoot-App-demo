package com.example.demo.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class AppConfig {
    @Autowired
    ClusterConfigurationProperties clusterProperties;

    @Bean
    public  RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(clusterProperties.getNodes());
    }

    @Bean
    public DefaultRedisScript<String> redisScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/seckill.wlua")));
        return redisScript;
    }
}
