package com.trafficbank.trafficbank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

}
