package com.teaShop.dao;

import com.teaShop.bean.SysUserRole;
import com.teaShop.bean.TeaGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaGoodsDao {
    /**
     *
     *
     */
    public List<TeaGoods> getGoodsByAddtime();
}
