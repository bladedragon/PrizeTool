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
        //使用json的序列化器
        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<TempAct>(TempAct.class);
//        JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }

    @Bean
    public RedisTemplate<String, TempAct> tempActSringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, TempAct> template = new RedisTemplate<String, TempAct>();
        template.setConnectionFactory(redisConnectionFactory);
        //使用json的序列化器
        StringRedisSerializer ser = new StringRedisSerializer();
//        JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }

    @Bean
    public RedisTemplate<String, String> StringRedistemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(redisConnectionFactory);
        //使用json的序列化器
        Jackson2JsonRedisSerializer ser = new Jackson2JsonRedisSerializer<String>(String.class);
//       JdkSerializationRedisSerializer ser = new JdkSerializationRedisSerializer();
        template.setDefaultSerializer(ser);                 //相当于key的序列化类型和value的序列化类型
        return template;
    }


}




