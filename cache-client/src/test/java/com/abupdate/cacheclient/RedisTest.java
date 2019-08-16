package com.abupdate.cacheclient;

import com.abupdate.cacheclient.dao.IRedisDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void removePatternTest(){
        String pattern = "*";
        Set keys = redisDao.getKeysPattern(pattern);
        for (Object obj:keys){
            System.out.println(obj.toString());
        }
        redisDao.removePattern("a*");
        Set keys2 = redisDao.getKeysPattern(pattern);
        for (Object obj:keys2){
            System.out.println(obj.toString());
        }
    }
}
