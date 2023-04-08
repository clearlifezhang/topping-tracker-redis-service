package com.clearlife.toppingtrackerredisservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String redisServerHost = System.getenv("REDIS_SERVER_HOST");
        if (redisServerHost == null || redisServerHost.isEmpty()) {
            redisServerHost = "redis-server"; // Default value
        }

        String redisServerPortStr = System.getenv("REDIS_SERVER_PORT");
        int redisServerPort = 6379; // Default value
        if (redisServerPortStr != null && !redisServerPortStr.isEmpty()) {
            redisServerPort = Integer.parseInt(redisServerPortStr);
        }

        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(redisServerHost, redisServerPort);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> myRedisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new ToppingRedisSerializer(objectMapper));
        template.setEnableTransactionSupport(true);
        return template;
    }
}
