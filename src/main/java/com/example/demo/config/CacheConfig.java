package com.example.demo.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public LettuceConnectionFactory cacheConnectionFactory() {

//         Redis 마스터 설정
        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration(
                Arrays.asList("192.168.158.160:7000", "192.168.158.160:7001", "192.168.158.160:7002", "192.168.158.160:7003", "192.168.158.160:7004", "192.168.158.160:7005")
        );

        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh(Duration.ofHours(1L))
                .build();

        SocketOptions socketOptions = SocketOptions.builder()
                .connectTimeout(Duration.ofSeconds(1))  // 연결 시도 타임아웃 설정
                .build();

        TimeoutOptions timeoutOptions = TimeoutOptions.builder()
                .fixedTimeout(Duration.ofSeconds(3))    // 모든 명령에 대해 고정된 타임아웃 적용
                .build();

        ClientOptions clientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
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

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }


    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(cacheConnectionFactory())
                .build();
    }
}
