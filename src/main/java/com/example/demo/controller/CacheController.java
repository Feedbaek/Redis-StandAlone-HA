//package com.example.demo.controller;
//
//import com.example.demo.service.CacheService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/cache")
//@RequiredArgsConstructor
//public class CacheController {
//
//    private final CacheService cacheService;
//
//    @GetMapping("/get")
//    public String get() {
//        String id = "1";
//        return cacheService.findAll(id);
//    }
//
//    @PostMapping("/put")
//    public String put() {
//        String id = "1";
//        return cacheService.save(id);
//    }
//}
