package com.abupdate.shopclient.mapper;

import com.abupdate.shopclient.bean.ShopCar;
import java.util.List;

public interface ShopCarMapper {
    int insert(ShopCar record);

    List<ShopCar> selectAll();
}