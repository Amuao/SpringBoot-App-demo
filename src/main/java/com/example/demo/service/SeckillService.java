package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface SeckillService {
    String doSeckill(String id);

    Boolean setSeckillNum(Integer number);
}
