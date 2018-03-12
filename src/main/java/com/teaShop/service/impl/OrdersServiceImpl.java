package com.teaShop.service.impl;

import com.teaShop.bean.TeaOrders;
import com.teaShop.dao.TeaOrdersDao;
import com.teaShop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService{

    @Autowired
    private TeaOrdersDao dao;
    public List<TeaOrders> getAll(TeaOrders teaOrders) {
        return dao.getAll(teaOrders);
    }
    public TeaOrders getOneById(String id){return dao.getOneById(id);}
    public int editOrder(TeaOrders teaOrders){
        return dao.editOrder(teaOrders);
    }

    public int orderIsHave(String orderid){ return dao.orderIsHave(orderid); }


}
