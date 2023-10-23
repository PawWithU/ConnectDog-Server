package com.pawwithu.connectdog.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<Long, String> redisTemplate;
    private final RedisTemplate<String, String> redisBlackListTemplate;

    public void set(Long key, String o, Integer minutes) {
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public String get(Long key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(Long key) {
        return redisTemplate.delete(key);
    }

    public boolean hasKey(Long key) {
        return redisTemplate.hasKey(key);
    }

    public void setBlackList(String key, String o, Integer milliSeconds) {
        redisBlackListTemplate.opsForValue().set(key, o, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public String getBlackList(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return redisBlackListTemplate.delete(key);
    }

    public boolean hasKeyBlackList(String key) {
        return redisBlackListTemplate.hasKey(key);
    }
}