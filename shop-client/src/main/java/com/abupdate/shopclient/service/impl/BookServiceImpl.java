package com.abupdate.shopclient.service.impl;

import com.abupdate.shopclient.bean.Book;
import com.abupdate.shopclient.mapper.BookMapper;
import com.abupdate.shopclient.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/12
 * @since V2.0
 */
@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private BookMapper bookmapper;

    @Override
    public void updateShopMsg(@RequestBody Book book) {
        bookmapper.updateByIdSelective(book);
    }
}
