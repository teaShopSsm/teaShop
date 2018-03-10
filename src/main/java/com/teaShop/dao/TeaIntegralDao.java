package com.teaShop.dao;

import com.teaShop.bean.TeaIntegral;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaIntegralDao {
    //查询所有
    public List<TeaIntegral> getAll(TeaIntegral teaIntegral);

    //根据id查询
    public TeaIntegral getOneById(int id);

    //更新
    public int updateIntegral(TeaIntegral teaIntegral);
}
