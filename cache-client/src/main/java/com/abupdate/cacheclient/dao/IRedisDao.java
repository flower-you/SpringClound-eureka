package com.abupdate.cacheclient.dao;

import java.io.IOException;
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

    /**
     * 批量模糊获取匹配的key
     *
     * @param pattern
     * @param limit 每次迭代数量
     * @return
     */
    Set<String> getKeysPattern(String pattern, int limit) throws IOException;

    /**
     * 通过hscan 批量获取和pattern匹配的key value
     *
     * @param hkey
     * @param pattern
     * @param size
     * @return
     */
    Map<Object, Object> getMapHscan(String hkey,String pattern, int size) throws IOException;


    /**
     * 批量删除key对应的value
     *
     * @param keys
     */
    void removes(final String... keys);

    /**
     * 批量模糊删除hash field
     * @param  hkey
     * @param pattern
     * @param limt
     */
    void hremovePattern(String hkey,String pattern,int limt) throws IOException;

    /**
     * string 批量模糊删除
     * @param pattern
     * @param limt
     */
    void removePattern(String pattern,int limt) throws IOException;

    /**
     * 删除key对应的value
     *
     * @param key
     */
    void remove(final String key);

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    boolean exists(final String key);

    /**
     * get key value
     *
     * @param key
     * @return
     */
    String get(final String key);

    /**
     * set key value ex
     *
     * @param key
     * @param value
     * @param expireTime 时间
     * @param timeUnit   时间单位
     * @return
     */
    boolean setExpire(String key, Object value, Long expireTime, TimeUnit timeUnit);


    /**
     * set key value
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, Object value);

    /**
     * hmsset key field value [field value]
     *
     * @param key
     * @param value
     * @return
     */
    boolean hmset(String key, Map<String, String> value);

    /**
     * hmget key field [field ...]
     *
     * @param key
     * @return
     */
    Map<String, String> hmget(String key);


}

