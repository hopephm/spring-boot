package kr.hope.springboot.infrastructure.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

/**
 * @see https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html
 */
@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun store(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun load(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }
}
