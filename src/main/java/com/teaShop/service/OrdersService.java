package com.teaShop.service;

import com.teaShop.bean.TeaOrders;

import java.util.List;

public interface OrdersService {

    /**
     * 查询所有订单
     * @return
     */
    public List<TeaOrders> getAll(TeaOrders teaOrders);
    public TeaOrders getOneById(int id);
    public int editOrder(TeaOrders teaOrders);

    public int orderIsHave(int orderid);
}
