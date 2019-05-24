package com.zhuhaoran.rebatemall.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhuHaoran
 * @className redisUtils
 * @date 2019/4/25
 * @description
 * 参考：https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
 */

@Component
public class RedisUtils {

    private static Integer DAY_TIME = 1;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    public  boolean setRedisByHashMap (String key,Map<String,Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key,map);
            redisTemplate.expire(key, DAY_TIME, TimeUnit.DAYS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public  Map<Object,Object> getRedisHashMapByKey (String key) {
        return redisTemplate.opsForHash().entries(key);
    }
}
