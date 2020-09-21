package com.example.demo.controller;

import com.example.demo.core.base.Result;
import com.example.demo.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class SeckillController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillService seckillService;

    @PostMapping("/seckill")
    public Result<String> seckill( @RequestBody Map<String, String> map  ){
        String key = "seckill:count";
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        log.info(Thread.currentThread().getName()+"--访问次数:"+increment);
        return Result.success(seckillService.doSeckill(map.get("id")));
    }

    @PostMapping("/seckill/number")
    public Result<Boolean> setSeckillNum(Integer number){
        return seckillService.setSeckillNum(number)?Result.success(Boolean.TRUE):Result.fail("500",Boolean.FALSE,"操作失败");
    }

}
