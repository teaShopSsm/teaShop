package com.teaShop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.TeaGoods;
import com.teaShop.dao.TeaGoodsDao;
import com.teaShop.global.GlobalPara;
import com.teaShop.service.TeaGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaGoodsServiceImpl implements TeaGoodsService {

    @Autowired
    private TeaGoodsDao dao;

    public List<TeaGoods> getGoodsByAddtime(TeaGoods teaGoods){
        return dao.getGoodsByAddtime(teaGoods);
    }

    public TeaGoods getGoodsByID(int id){
        return dao.getGoodsByID(id);
    }

    /**
     *根据新增商品
     *
     */
    public int addGoods(TeaGoods teaGoods){
        return dao.addGoods(teaGoods);
    }

    /**
     *根据id更新商品
     *
     */
    public int updateGoods(TeaGoods teaGoods){
        return dao.updateGoods(teaGoods);
    }
    /**
     *根据id删除商品
     *
     */
    public int deleteGoods(int id){
        return dao.deleteGoods(id);
    }

}
