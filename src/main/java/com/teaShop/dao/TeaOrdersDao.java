package com.teaShop.dao;

import com.teaShop.bean.TeaIntegral;
import com.teaShop.bean.TeaOrderGoods;
import com.teaShop.bean.TeaOrders;

import java.util.List;

import com.teaShop.bean.dto.TeaOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TeaOrdersDao {
    int addOrder(TeaOrders teaOrders);

    void addOrderItem(TeaOrderGoods teaOrderGoods);

    void addIntegral(TeaIntegral teaIntegral);

    List<TeaOrderVO> getOrderList(Integer userId);

    List<TeaIntegral> getIntegralList(Integer userId);

    int orderIsHave(int orderid);

    public List<TeaOrders> getAll(TeaOrders teaOrders);

    public TeaOrders getOneById(int id);

    public int editOrder(TeaOrders teaOrders);
    BigDecimal getIntegralSum(Integer userId);

}