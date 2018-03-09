package com.teaShop.service.impl;


import com.teaShop.bean.TeaGoods;
import com.teaShop.dao.TeaGoodsDao;
import com.teaShop.service.TeaGoodsWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaGoodsWebServiceImpl implements TeaGoodsWebService {

    @Autowired
    private TeaGoodsDao teaGoodsDao;
    public List<TeaGoods> getGoodsByAddtime(){
        try {
            return teaGoodsDao.getGoodsByAddtime();
        } catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
