package com.example.demo.controller;

import com.example.demo.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("/get1")
    public String get1() {
        String id = "1";
        return cacheService.findAll(id);
    }

    @GetMapping("/get2")
    public String get2() {
        String id = "2";
        return cacheService.findAll(id);
    }

    @PostMapping("/put1")
    public String put1() {
        String id = "1";
        return cacheService.save(id);
    }

    @PostMapping("/put2")
    public String put2() {
        String id = "2";
        return cacheService.save(id);
    }
}
