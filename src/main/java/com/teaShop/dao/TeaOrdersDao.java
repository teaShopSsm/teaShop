package com.teaShop.dao;

import com.teaShop.bean.TeaOrders;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeaOrdersDao {

    public int orderIsHave(int orderid);
}