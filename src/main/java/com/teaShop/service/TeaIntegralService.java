package com.teaShop.service;

import com.teaShop.bean.TeaIntegral;

import java.util.List;

public interface TeaIntegralService {
    //查询所有
    public List<TeaIntegral> getAll(TeaIntegral teaIntegral);

    //根据id查询
    public TeaIntegral getOneById(int id);

    //更新
    public int updateIntegral(TeaIntegral teaIntegral);

}
