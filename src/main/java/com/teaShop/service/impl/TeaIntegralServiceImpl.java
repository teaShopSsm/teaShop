package com.teaShop.service.impl;

import com.teaShop.bean.TeaIntegral;
import com.teaShop.dao.TeaIntegralDao;
import com.teaShop.service.TeaIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaIntegralServiceImpl implements TeaIntegralService {

    @Autowired
    private TeaIntegralDao dao;

    //查询所有
    public List<TeaIntegral> getAll(TeaIntegral teaIntegral){
        return dao.getAll(teaIntegral);
    }

    //根据id查询
    public TeaIntegral getOneById(int id){
        return dao.getOneById(id);
    }

    //更新
    public int updateIntegral(TeaIntegral teaIntegral){
        return dao.updateIntegral(teaIntegral);
    }
}
