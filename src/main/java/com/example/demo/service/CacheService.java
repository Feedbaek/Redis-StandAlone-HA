package com.example.demo.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheManager = "redisCacheManager", cacheNames = "cache")
public class CacheService {

    @Cacheable(key = "#p0", unless = "#result == null")
    public String findAll(String id) {
        return "Not Found" + id;
    }

    @CachePut(key = "#p0")
    public String save(String id) {
        return "Saved" + id;
    }
}
