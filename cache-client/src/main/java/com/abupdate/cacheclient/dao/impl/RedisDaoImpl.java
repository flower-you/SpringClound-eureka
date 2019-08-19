package com.abupdate.cacheclient.dao.impl;

import com.abupdate.cacheclient.dao.IRedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

    private Logger logger = LoggerFactory.getLogger(RedisDaoImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void removes(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    @Override
    public void hremovePattern(String hkey, String pattern, int limt) throws IOException {
        Map<Object, Object> map = this.getMapHscan(hkey, pattern, limt);
        Set<Map.Entry<Object, Object>> entrySet = map.entrySet();
        for (Map.Entry<Object, Object> en : entrySet) {
            redisTemplate.delete(en.getKey());
        }
    }

    @Override
    public void removePattern(String pattern, int limt) throws IOException {
        Cursor<String> cursor = this.scan(pattern, limt);
        try {
            while (cursor.hasNext()) {
                String key = cursor.next();
                redisTemplate.delete(key);
            }
        } finally {
            cursor.close();
        }

    }

    @Override
    public void remove(String key) {
        if (exists(key)) {
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
        if (null == result) {
            return null;
        }
        return result.toString();
    }


    @Override
    public boolean setExpire(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        ValueOperations operations = redisTemplate.opsForValue();
        operations.set(key, value);
        redisTemplate.expire(key, expireTime, timeUnit);
        result = true;
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

    @Override
    public boolean hmset(String key, Map<String, String> value) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().putAll(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, String> hmget(String key) {
        Map<String, String> result = null;
        try {
            result = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<Object, Object> getMapHscan(String hkey, String pattern, int size) throws IOException {
        Map<Object, Object> result = new ConcurrentHashMap<Object, Object>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(hkey, ScanOptions.scanOptions().match(pattern).count(size).build());
        try {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                result.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("=========== redisDao getMapHscan 异常 ====>{}", e.getMessage());
        } finally {
            if (!StringUtils.isEmpty(cursor)) {
                cursor.close();
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Cursor<String> scan(String pattern, int limit) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        return (Cursor) redisTemplate.executeWithStickyConnection(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize);
            }
        });
    }

    @Override
    public Set<String> getKeysPattern(String pattern, int limit) throws IOException {
        Set<String> keys = new HashSet<String>();
        Cursor<String> cursor = this.scan(pattern, limit);
        try {
            while (cursor.hasNext()) {
                String s = cursor.next();
                keys.add(s);
            }
        } finally {
            if (!StringUtils.isEmpty(cursor)) {
                cursor.close();
            }
        }
        return keys;
    }
}
