import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class CacheConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.registerCustomCache("ManageCache",
                Caffeine.newBuilder()
                        .expireAfterAccess(20, TimeUnit.MINUTES)
                        .removalListener((key, value, cause) -> log.info(">> 删除缓存{},原因 {}", key, cause))
                        .recordStats()
                        .build());

        return cacheManager;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericFastJsonRedisSerializer())
                );
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }

}

