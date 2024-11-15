package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

@Configuration
@EnableRedisHttpSession
public class SessionConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

//         Redis 마스터 설정
        RedisStaticMasterReplicaConfiguration redisConfig = new RedisStaticMasterReplicaConfiguration("localhost", 6379);

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
                .clientOptions(clientOptions)
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }


    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}