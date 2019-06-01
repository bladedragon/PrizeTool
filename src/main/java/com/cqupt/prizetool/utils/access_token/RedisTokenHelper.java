package com.cqupt.prizetool.utils.access_token;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisTokenHelper {

    @Autowired(required = true)
    RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public void save(String tokenType, String Token) {
        this.stringRedisTemplate.opsForValue().set(tokenType, Token, 5400, TimeUnit.SECONDS);

    }

    public String getToken(String tokenType) {
        return stringRedisTemplate.opsForValue().get(tokenType);
    }


    public void saveObject(String key, Object obj, long timeout) {
        this.redisTemplate.opsForValue().set(key, obj, timeout, TimeUnit.SECONDS);
    }


    public void saveObject(String key, String string) {
        this.stringRedisTemplate.opsForValue().set(key, string);
    }


    public String getObject(String key) {
        String str;
        try {
            str = this.stringRedisTemplate.opsForValue().get(key);
            if(str==null){
                return "1";
            }
        }catch(NullPointerException e){
           log.error("ZLOG==>NullPoint Exception");
            return "1";
        }

        return str;
    }

    public void removeObject(String key) {
        redisTemplate.delete(key);
    }
}
