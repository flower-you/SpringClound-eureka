package com.abupdate.shopclient.service;

import com.abupdate.shopclient.bean.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/12
 * @since V2.0
 */
@FeignClient("cache-client")
public interface IBookService {
    /**
     * 根据商品id更新商品信息
     * @param book
     */
    @PostMapping("/shopRedisApi/updateShop")
    void updateShopMsg(@RequestBody Book book);
}
