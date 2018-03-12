package com.teaShop.service.impl;


import com.teaShop.bean.TeaGoods;
import com.teaShop.bean.TeaIntegral;
import com.teaShop.bean.TeaOrderGoods;
import com.teaShop.bean.TeaOrders;
import com.teaShop.bean.dto.TeaGoodsDTO;
import com.teaShop.bean.dto.TeaOrderVO;
import com.teaShop.dao.TeaGoodsDao;
import com.teaShop.dao.TeaOrdersDao;
import com.teaShop.service.SysUserService;
import com.teaShop.service.TeaGoodsWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TeaGoodsWebServiceImpl implements TeaGoodsWebService {

    @Autowired
    private TeaGoodsDao teaGoodsDao;
    @Autowired
    private TeaOrdersDao teaOrdersDao;

    public List<TeaGoods> getGoodsByAddtime(String goodsName) {
        try {
            TeaGoods teaGoods = new TeaGoods();
            teaGoods.setGoodsname(goodsName);
            return teaGoodsDao.getGoodsByAddtime(teaGoods);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TeaGoods getGoodsDetail(Integer id) {
        try {
            return teaGoodsDao.getGoodsByID(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveOrder(TeaGoodsDTO teaGoodsDTO, Integer userId, String userName) {
        //保存订单
        TeaOrders teaOrders = new TeaOrders();
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        teaOrders.setId(orderId);
        teaOrders.setAddress(teaGoodsDTO.getAddress());
        teaOrders.setAddtime(new Date());
        teaOrders.setCode("" + System.currentTimeMillis());
        teaOrders.setStatus(1);
        teaOrders.setQuantity(teaGoodsDTO.getNumber());
        teaOrders.setTelephone(teaGoodsDTO.getMobile());
        BigDecimal totalPrice = teaGoodsDTO.getPrice().multiply(new BigDecimal(teaGoodsDTO.getNumber()));
        teaOrders.setTotalprice(totalPrice);
        teaOrders.setUserid(String.valueOf(userId));
        teaOrders.setUsername(teaGoodsDTO.getUsername());
        teaOrders.setUserid(String.valueOf(userId));
        teaOrders.setUsername(teaGoodsDTO.getUsername());
        teaOrdersDao.addOrder(teaOrders);
        //保存订单项
        TeaOrderGoods teaOrderGoods = new TeaOrderGoods();
        teaOrderGoods.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        teaOrderGoods.setGoodsid(teaGoodsDTO.getId());
        teaOrderGoods.setGoodsname(teaGoodsDTO.getGoodsname());
        teaOrderGoods.setGoodsPrice(teaGoodsDTO.getPrice());
        teaOrderGoods.setGoodsQuantity(teaGoodsDTO.getNumber());
        teaOrderGoods.setGoodspic(teaGoodsDTO.getGoodspic());
        teaOrderGoods.setOrderid(String.valueOf(orderId));
        teaOrdersDao.addOrderItem(teaOrderGoods);
        //新增积分
        TeaIntegral teaIntegral = new TeaIntegral();
        teaIntegral.setOperator("1");//1加
        teaIntegral.setOrderid(teaOrders.getId());
        //teaIntegral.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        teaIntegral.setOperator("1");//1加
        teaIntegral.setOrderid(orderId);
        teaIntegral.setOrderprice(totalPrice);
        teaIntegral.setQuantity(totalPrice.divide(new BigDecimal(10)));
        teaIntegral.setUserid(userId);
        teaIntegral.setUsername(userName);
        teaIntegral.setOrderno(teaOrders.getCode());
        teaOrdersDao.addIntegral(teaIntegral);
    }


    public List<TeaOrderVO> getOrderList(Integer userId) {
        return teaOrdersDao.getOrderList(userId);
    }

    public List<TeaIntegral> getIntegralList(Integer userId) {
        return teaOrdersDao.getIntegralList(userId);
    }

    public BigDecimal getIntegralSum(Integer userId) {
        return teaOrdersDao.getIntegralSum(userId);
    }
}
