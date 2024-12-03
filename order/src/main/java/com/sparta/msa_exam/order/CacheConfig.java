package com.sparta.msa_exam.order;

import com.sparta.msa_exam.order.orders.OrderResponseDto;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

  @Bean
  // CacheManager로 진행해도 정상 동작
  public RedisCacheManager cacheManager(
      RedisConnectionFactory redisConnectionFactory  // jedis 의존성 추가해줘야 인식됨
  ) {

    RedisCacheConfiguration getOrderConfiguration = getOrderCacheConfiguration();
    RedisCacheConfiguration checkProductConfiguration = getCheckProductConfiguration();

    return RedisCacheManager
        .builder(redisConnectionFactory)
//        .cacheDefaults(configuration)  // 기본 설정 제외
        .withCacheConfiguration("orderCache", getOrderConfiguration) // orderCache 만 활성화
        .withCacheConfiguration("checkProductIsPresent", getCheckProductConfiguration())
        .build();
  }

  private RedisCacheConfiguration getOrderCacheConfiguration() {
    return RedisCacheConfiguration
        .defaultCacheConfig()    // default를 사용하여 대부분 기본 설정
        // null을 캐싱 할것인지
        .disableCachingNullValues()
        // 기본 캐시 유지 시간 (Time To Live)
        .entryTtl(Duration.ofSeconds(60))
        // 캐시를 구분하는 접두사 설정(cache_)
        .computePrefixWith(CacheKeyPrefix.simple())
        // OrderResponseDto 에 대해서만 직렬화
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new Jackson2JsonRedisSerializer<>(OrderResponseDto.class)
            )
        );
  }


  private RedisCacheConfiguration getCheckProductConfiguration() {
    return RedisCacheConfiguration
        .defaultCacheConfig()    // default를 사용하여 대부분 기본 설정
        // null을 캐싱 할것인지
        .disableCachingNullValues()
        // 기본 캐시 유지 시간 (Time To Live)
        .entryTtl(Duration.ofSeconds(10))
        // 캐시를 구분하는 접두사 설정(cache_)
        .computePrefixWith(CacheKeyPrefix.simple())
        // OrderResponseDto 에 대해서만 직렬화
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new Jackson2JsonRedisSerializer<>(Boolean.class)
            )
        );
  }


}