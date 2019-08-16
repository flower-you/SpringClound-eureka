package com.abupdate.shopclient.controller;

import com.abupdate.shopclient.bean.Book;
import com.abupdate.shopclient.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/13
 * @since V2.0
 */
@Controller
public class BookController {
    @Autowired
    private IBookService bookService;

    @ResponseBody
    @RequestMapping(value = "/updateShopMsg",method = RequestMethod.POST)
    public void updateShopMsg(Book book){
        bookService.updateShopMsg(book);
    }
}
