package com.trafficbank.trafficbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LettuceService {

    private final StringRedisTemplate redisTemplate;

    public Long getSequenceNumber(String key, Long defaultValue) {
        Long sequenceNumber = redisTemplate.opsForValue().increment(key);

        if (Objects.isNull(sequenceNumber) || sequenceNumber < defaultValue) {
            redisTemplate.opsForValue().set(key, String.valueOf(defaultValue));
            return defaultValue;
        }

        return sequenceNumber;
    }

    public boolean lock(String key, long expire) {
        String lockKey = "LOCK:" + key;
        return Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, Instant.now().toString(), Duration.ofSeconds(expire)));
    }


    public boolean unlock(String key) {
        String lockKey = "LOCK:" + key;
        return Boolean.TRUE.equals(redisTemplate.delete(lockKey));
    }

    @Async
    public void asyncUnlock(String key) {
        String lockKey = "LOCK:" + key;
        redisTemplate.delete(lockKey);
    }
}
