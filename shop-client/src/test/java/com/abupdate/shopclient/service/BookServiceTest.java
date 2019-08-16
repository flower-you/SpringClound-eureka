package com.abupdate.shopclient.service;

import com.abupdate.shopclient.ShopClientApplicationTests;
import com.abupdate.shopclient.bean.Book;
import com.abupdate.shopclient.mapper.BookMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/13
 * @since V2.0
 */
public class BookServiceTest extends ShopClientApplicationTests {
    @Autowired
//    private IBookService bookService;
    private BookMapper bookMapper;

    @Test
    public void updateShopMsgTest(){
        Book book = new Book();
        book.setId(1);
        book.setPrice(9.5F);
        book.setRebate(new BigDecimal(0.8));
        bookMapper.updateByIdSelective(book);
    }

}
