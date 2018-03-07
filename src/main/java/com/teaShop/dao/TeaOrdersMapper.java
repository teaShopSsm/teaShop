package com.teaShop.dao;

import com.teaShop.bean.TeaOrders;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeaOrdersMapper {

    int deleteByPrimaryKey(String id);

    int insert(TeaOrders record);

    int insertSelective(TeaOrders record);

    TeaOrders selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TeaOrders record);

    int updateByPrimaryKey(TeaOrders record);
}