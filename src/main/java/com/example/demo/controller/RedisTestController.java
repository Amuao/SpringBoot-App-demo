package com.example.demo.controller;

import com.example.demo.core.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Optional;

@RestController
@Slf4j
public class RedisTestController {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @RequestMapping(value = "/redis/add/{key}/{value}", method = RequestMethod.GET)
    public Result<Boolean> add(@PathVariable("key")String key, @PathVariable("value")String value) throws Throwable {
        ValueOperations<String, Serializable> opsForValue  = redisTemplate.opsForValue();
        opsForValue.set(key,value);
        Serializable o = Optional.ofNullable(opsForValue.get(key)).orElseThrow(Throwable::new);
        log.info("redis add key = {} ,value = {} ",key,o );
        return Result.success(true);
    }
}
