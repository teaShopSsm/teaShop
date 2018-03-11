package com.teaShop.dao;

import com.teaShop.bean.TeaIntegral;
import com.teaShop.bean.TeaOrderGoods;
import com.teaShop.bean.TeaOrders;
import com.teaShop.bean.dto.TeaOrderVO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TeaOrdersDao {
    void addOrder(TeaOrders teaOrders);

    void addOrderItem(TeaOrderGoods teaOrderGoods);

    void addIntegral(TeaIntegral teaIntegral);

    List<TeaOrderVO> getOrderList(Integer userId);

    List<TeaIntegral> getIntegralList(Integer userId);

    BigDecimal getIntegralSum(Integer userId);

}