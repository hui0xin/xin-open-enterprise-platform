package com.xin.commons.multi.redis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private RedisTemplate<String,Object> redis1;

    @Resource
    private RedisTemplate<String,Object> redis2;

    @Resource
    private RedisTemplate<String,Object> redis3;

    @GetMapping("/test")
    public Object test() {
        redisTemplate.opsForValue().set("test", "TestController-redis");
        return redisTemplate.opsForValue().get("test");
    }

    @GetMapping("/test1")
    public Object test1() {
        redis1.opsForValue().set("test", "TestController-redis1");
        return redis1.opsForValue().get("test");
    }

    @GetMapping("/test2")
    public Object test2() {
        redis2.opsForValue().set("test", "TestController-redis2");
        return redis2.opsForValue().get("test");
    }

    @GetMapping("/test3")
    public Object test3() {
        redis3.opsForValue().set("test", "TestController-redis3");
        return redis3.opsForValue().get("test");
    }
}