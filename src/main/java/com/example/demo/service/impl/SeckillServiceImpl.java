package com.example.demo.service.impl;

import com.example.demo.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisScript<Long> redisScript;

    @Override
    public String doSeckill(String userID ) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Object res = redisTemplate.execute(redisScript, Arrays.asList("{seckill}:" + date + ":kc", "{seckill}:" + date + ":user"),
                Arrays.asList(userID));
        long a = res == null ? -1 : (Long) res;
        String message = "";
        if( a == 1 ){
            message = "抢购成功！";
        }else if( a== 0 ){
            message = "商品已经被抢完啦！";
        }else if(a ==2) {
            message = "每个账号限购一次！请勿重复操作。";
        }else{
            message ="活动尚未开始！";
        }
        log.info(userID+":"+message);
        return message;
    }

    @Override
    public Boolean setSeckillNum(Integer number) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String seckillNumKey= "{seckill}:" + date + ":kc";
        try{
            redisTemplate.opsForValue().set(seckillNumKey,number);
            return Boolean.TRUE;
        }catch (Exception e){
            log.error("设置秒杀商品数量失败，尝试清除数据");
        }
        return Boolean.FALSE;
    }
}
