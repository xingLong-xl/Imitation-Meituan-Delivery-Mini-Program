package com.sky.service.impl;

import com.sky.config.RedisConfiguration;
import com.sky.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void setStatus(Integer status) {
        redisTemplate.opsForValue().set("KEY",status);
    }

    @Override
    public int getStatus() {
        return (int)redisTemplate.opsForValue().get("KEY");
    }
}
