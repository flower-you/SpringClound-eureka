package com.abupdate.shopclient.mapper;

import com.abupdate.shopclient.bean.Book;
import java.util.List;

public interface BookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    Book selectByPrimaryKey(Integer id);

    List<Book> selectAll();

    void updateByIdSelective(Book book);

}