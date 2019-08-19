package com.abupdate.cacheclient;

import com.abupdate.cacheclient.bean.Book;
import com.abupdate.cacheclient.dao.IRedisDao;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/14
 * @since V2.0
 */
public class RedisTest extends CacheClientApplicationTests {
    @Autowired
    private IRedisDao redisDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void getKeysPatternTest(){
        Set<String> keys = null;
        try {
          keys = redisDao.getKeysPattern("b*", 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(keys)){
            for (String s:keys){
                System.out.println(s);
            }
        }
    }

    @Test
    public void removePatternTest(){
        String pattern = "*";
        Set keys;
        try {
            keys = redisDao.getKeysPattern(pattern,10);
            for (Object obj:keys){
                System.out.println(obj.toString());
            }
            redisDao.removePattern("cc*",2);
            Set keys2 = redisDao.getKeysPattern(pattern,10);
            for (Object obj:keys2){
                System.out.println(obj.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scanTest(){

    }

    @Test
    public void getMapHscanTest(){
        Map<String,String> map = new HashMap<String,String>();
        Book book = new Book();
        book.setAuth("flower");
        book.setPrice(16.7F);
        String bookStr = JSONObject.toJSONString(book);
        map.put("bookForScan",bookStr);
        redisDao.hmset("testScan",map);
        Map<Object, Object> testScan = null;
        try {
            testScan = redisDao.getMapHscan("testScan", "bookForScan",2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<Object, Object>> entries = testScan.entrySet();
        for (Map.Entry<Object, Object> en:entries){
            System.out.println("key: "+en.getKey()+"value: "+en.getValue());
        }
    }

    @Test
    public void testMap(){
        Map<Object,Object> map = new HashMap<Object, Object>();
        map.put(1,"1");
        map.put("1",1);
        map.put("2",2);
        map.put(3,3);
        Book book = new Book();
        book.setAuth("flower");
        book.setPrice(16.7F);
        map.put("bookForScan",book);

        Set<Map.Entry<Object, Object>> entries = map.entrySet();

        for (Map.Entry<Object,Object> ma:entries){
            System.out.println(" key: "+ma.getKey()+"value:"+ma.getValue());
        }
    }
}
