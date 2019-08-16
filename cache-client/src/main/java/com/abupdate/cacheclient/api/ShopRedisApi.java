package com.abupdate.cacheclient.api;

import com.abupdate.cacheclient.bean.Book;
import com.abupdate.cacheclient.dao.IRedisDao;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/12
 * @since V2.0
 */
@RestController
@RequestMapping("/shopRedisApi")
public class ShopRedisApi {

    private Logger logger = LoggerFactory.getLogger(ShopRedisApi.class);

    @Autowired
    private IRedisDao redisDao;

    @PostMapping(value = "/updateShop")
    public void updateShopInRedis(@RequestBody Book book){
        logger.info("=========ShopRedisApi book{}========>",book);
        redisDao.set("book"+book.getId(), JSON.toJSONString(book));
    }
}
