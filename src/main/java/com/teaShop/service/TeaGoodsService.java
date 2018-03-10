package com.teaShop.service;

import com.teaShop.bean.TeaGoods;

import java.util.List;

public interface TeaGoodsService {
    /**
     *查询所有商品
     *
     */
    public List<TeaGoods> getGoodsByAddtime(TeaGoods teaGoods );

    /**
     *根据id查询商品
     *
     */
    public TeaGoods getGoodsByID(int id);

    /**
     *根据新增商品
     *
     */
    public int addGoods(TeaGoods teaGoods);

    /**
     *根据id更新商品
     *
     */
    public int updateGoods(TeaGoods teaGoods);
    /**
     *根据id删除商品
     *
     */
    public int deleteGoods(int id);
}
