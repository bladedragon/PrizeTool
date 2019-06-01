package com.cqupt.prizetool.config;


import com.cqupt.prizetool.model.TempAct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.net.UnknownHostException;

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<Object, TempAct> tempActRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, TempAct> template = new RedisTemplate<Object, TempAct>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<TempAct>(TempAct.class);

        template.setDefaultSerializer(ser);
        return template;
    }

    @Bean
    public RedisTemplate<String, TempAct> tempActSringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, TempAct> template = new RedisTemplate<String, TempAct>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer ser = new StringRedisSerializer();

        template.setDefaultSerializer(ser);
        return template;
    }

    @Bean
    public RedisTemplate<String, String> StringRedistemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<String>(String.class);

        template.setDefaultSerializer(ser);
        return template;
    }


}




