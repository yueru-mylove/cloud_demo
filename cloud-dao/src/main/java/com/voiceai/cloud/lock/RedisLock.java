package com.voiceai.cloud.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月18日 23:15:00
 */
@Component
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    private Long spineMills = 100L;

    private Long defaultLockTime = 5000L;

    public boolean lock(String method, String requestId) {

        String script = "if redis.call('setNx', KEYS[1], ARGV[1]) then if redis.call('get', KEYS[1], ARGV[1]) then return redis.call('expire', KEY[1], ARGV[2]) else 0 end end";

        RedisScript<String> script1 =  new DefaultRedisScript<>(script, String.class);
        Object result = redisTemplate.execute(script1, Collections.singletonList(method), requestId, defaultLockTime);
        if ("success".equals(result)) {
            return true;
        }
        return false;
    }

    public boolean unlock(String method, String value) {
        String content = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else 0 end";

        RedisScript<String> script = new DefaultRedisScript<>(content, String.class);
        Object result = redisTemplate.execute(script, Collections.singletonList(method), value);
        if ("success".equals(result)) {
            return true;
        }
        return false;
    }

}
