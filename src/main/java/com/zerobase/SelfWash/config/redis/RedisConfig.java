package com.zerobase.SelfWash.config.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

  public RedisTemplate<String, Object> redisTemplate (RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);

    //직렬화 설정
    template.setKeySerializer(new StringRedisSerializer()); // 키를 문자열로 저장
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class)); // 값을 JSON형식으로 직렬화, 객체를 JSON으로 변환하고 역직렬화 시 JSON을 객체로 변환
    template.setHashKeySerializer(new StringRedisSerializer()); // 해시의 키는 문자열로 직렬화
    template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class)); // 해시의 값은 JSON으로 직렬화
    template.afterPropertiesSet();

    return template;
  }

  //TODO 추후에 고객이 기계 사용현황 조회 시 캐시 데이터에서 가져오도록 함.
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
    return RedisCacheManager.builder(factory)
        .cacheDefaults(cacheConfiguration())
        .transactionAware() // 트랜잭션이 활성화된 경우 캐시 작업이 트랜잭션에 참여하도록 함.
        .build();

  }

  private RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new StringRedisSerializer()))
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer()));
  // 매장 데이터는 항상 유효해야 하고 @CachePut, @CacheEvict 을 이용하여 캐시 데이터를 관리할것이기 때문에 TTL을 따로 설정하지 않음.
  }


}
