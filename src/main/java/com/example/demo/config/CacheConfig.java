package com.example.demo.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.defaultConfiguration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(name = "cacheConnectionFactory")
    public LettuceConnectionFactory redisConnectionFactory() {

//         Redis 마스터 설정
        RedisStaticMasterReplicaConfiguration redisConfig = new RedisStaticMasterReplicaConfiguration("localhost", 6379);
        redisConfig.addNode("localhost", 6381);
        redisConfig.addNode("localhost", 6382);

        SocketOptions socketOptions = SocketOptions.builder()
                .connectTimeout(Duration.ofSeconds(1))  // 연결 시도 타임아웃 설정
                .build();

        TimeoutOptions timeoutOptions = TimeoutOptions.builder()
                .fixedTimeout(Duration.ofSeconds(3))    // 모든 명령에 대해 고정된 타임아웃 적용
                .build();

        ClientOptions clientOptions = ClientOptions.builder()
                .autoReconnect(true)             // 자동 재연결 설정
                .socketOptions(socketOptions)            // 소켓 옵션 설정 적용
                .timeoutOptions(timeoutOptions)          // 타임아웃 옵션 설정 적용
                .build();

//         Lettuce 클라이언트 설정
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED) // 슬레이브에서 읽기를 우선시
                .clientOptions(clientOptions)
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }


    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory())
                .build();
    }
}
