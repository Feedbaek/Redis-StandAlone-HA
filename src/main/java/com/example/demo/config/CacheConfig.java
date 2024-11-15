//package com.example.demo.config;
//
//import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//
//import java.time.Duration;
//
////@EnableCaching
//@Configuration
//public class CacheConfig {
//    @Bean
//    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("cache", RedisCacheConfiguration
////                        .defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
////                .withCacheConfiguration("cache", RedisCacheConfiguration
//                        .defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));
//    }
//}
