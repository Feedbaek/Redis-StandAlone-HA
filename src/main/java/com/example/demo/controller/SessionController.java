package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/redis")
@Slf4j(topic = "REDIS")
@RequiredArgsConstructor
public class SessionController {

    @Getter
    @ToString
    public static class TestData implements Serializable {
        private final String  testData = "testData";
    }

    @GetMapping("/get")
    public String sessionGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        return "redis/session - name:" + session.getAttribute("testData");
        Object testData = session.getAttribute("testData");
        return "redis/session - name:" + testData;
    }

    @PostMapping("/set")
    public String session(HttpServletRequest request) {
        HttpSession session = request.getSession();
        TestData testData = new TestData();
//        Long testData = 10L;
//        String testData = "testData";
        session.setAttribute("testData", testData);
        return "redis/session set";
    }
}
