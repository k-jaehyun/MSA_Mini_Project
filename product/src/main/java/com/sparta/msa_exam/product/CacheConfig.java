package com.sparta.msa_exam.product;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class CacheConfig {

  private <T> RedisCacheConfiguration getCacheConfiguration(Class<T> valueType) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 제네릭 타입 정보를 포함한 직렬화기 생성
    Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(
        Object.class);
    serializer.setObjectMapper(objectMapper);

    return RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues()
        .entryTtl(Duration.ofSeconds(60))  // 기본 캐시 유지 시간
        .computePrefixWith(CacheKeyPrefix.simple())
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(serializer)
        );
  }
}