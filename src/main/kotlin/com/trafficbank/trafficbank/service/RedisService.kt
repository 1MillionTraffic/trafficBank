package com.trafficbank.trafficbank.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(private val stringRedisTemplate: StringRedisTemplate, private val objectMapper: ObjectMapper) {

    fun <T : Any> get(key: String, clazz: Class<T>): T? {
        val value = stringRedisTemplate.opsForValue().get(key) ?: return null

        return try {
            objectMapper.readValue(value, clazz)
        } catch (e: Exception) {
            null
        }
    }

    fun set(key: String, value: Any, duration: Duration) =
        stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), duration)


    fun increment(key: String): Long = stringRedisTemplate.opsForValue().increment(key)!!


    fun clear(key: String) = stringRedisTemplate.unlink(key)


    fun addSet(key: String, value: Any) =
        stringRedisTemplate.opsForSet().add(key, objectMapper.writeValueAsString(value))!!

    fun isMemberSet(key: String, value: Any) =
        stringRedisTemplate.opsForSet().isMember(key, objectMapper.writeValueAsString(value)) ?: false


}
