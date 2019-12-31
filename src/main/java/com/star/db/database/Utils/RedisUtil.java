package com.star.db.database.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    @SuppressWarnings(value = "unchecked")
    public void save(String key,Object value,Long time){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,time, TimeUnit.SECONDS);

    }

    public <T> T get(String key,Class<T> clazz){
        T valueObj = null;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object value = valueOperations.get(key);
        ObjectMapper objectMapper = new ObjectMapper();
        String valueStr = "";
        try {
            valueStr = objectMapper.writeValueAsString(value);
            valueObj = objectMapper.readValue(valueStr,clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueObj;
    }

}
