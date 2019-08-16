package com.abupdate.cacheclient.dao;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 〈redis操作〉
 *
 * @author wangjuhua
 * @create 2019/7/30
 * @since V2.0
 */
public interface IRedisDao {

    public Set getKeysPattern(String pattern);
    /**
     * 批量删除key对应的value
     * @param keys
     */
    public void removes(final String... keys);

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern);

    /**
     * 删除key对应的value
     * @param key
     */
    public void remove(final String key);

    /**
     * 判断缓存中是否有对应的key
     * @param key
     * @return
     */
    public boolean exists(final String key);

    /**
     * get key value
     * @param key
     * @return
     */
    public String get(final String key);

    /**
     * set key value ex
     * @param key
     * @param value
     * @param expireTime 时间
     * @param timeUnit 时间单位
     * @return
     */
    public boolean setExpire(String key, Object value, Long expireTime, TimeUnit timeUnit);



    /**
     * set key value
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value);

    /**
     * hmsset key field value [field value]
     * @param key
     * @param value
     * @return
     */
    public boolean hmset(String key, Map<String, String> value);

    /**
     * hmget key field [field ...]
     * @param key
     * @return
     */
    public Map<String, String> hmget(String key);
}

