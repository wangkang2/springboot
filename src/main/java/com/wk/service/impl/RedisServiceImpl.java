package com.wk.service.impl;

import com.wk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key,value,expireTime,TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean remove(String key) {
        boolean result = false;
        try{
            if(exists(key)){
                redisTemplate.delete(key);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean remove(List<String> keys) {
        boolean result = false;
        try {
            for (String key:keys) {
                remove(key);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


}
