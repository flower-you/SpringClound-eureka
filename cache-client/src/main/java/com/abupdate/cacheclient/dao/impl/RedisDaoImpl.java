package com.abupdate.cacheclient.dao.impl;

import com.abupdate.cacheclient.dao.IRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 〈redis操作封装〉
 *
 * @author wangjuhua
 * @create 2019/7/30
 * @since V2.0
 */
@Repository
public class RedisDaoImpl implements IRedisDao {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void removes(String... keys) {
        for (String key:keys){
            remove(key);
        }
    }

    @Override
    public void removePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void remove(String key) {
        if (exists(key)){
            redisTemplate.delete(key);
        }
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public String get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if (null == result){
            return null;
        }
        return result.toString();
    }


    @Override
    public boolean setExpire(String key, Object value, Long expireTime,TimeUnit timeUnit) {
        boolean result = false;
        try{
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expireTime, timeUnit);
            result = true;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> opredis = redisTemplate.opsForValue();
            opredis.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//       hmset key field value [field value]
    @Override
    public boolean hmset(String key, Map<String, String> value) {
        boolean result = false;
        try{
            redisTemplate.opsForHash().putAll(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

// hmget key field [field ...]
    @Override
    public Map<String, String> hmget(String key) {
        Map<String,String> result = null;
        try{
            result = redisTemplate.opsForHash().entries(key);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Set getKeysPattern(String pattern){
       Set<Serializable> keys = redisTemplate.keys(pattern);
       return keys;
    }
}
