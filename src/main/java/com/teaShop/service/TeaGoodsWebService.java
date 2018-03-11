package com.teaShop.service;

import com.teaShop.bean.TeaGoods;
import com.teaShop.bean.TeaIntegral;
import com.teaShop.bean.dto.TeaGoodsDTO;
import com.teaShop.bean.dto.TeaOrderVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface TeaGoodsWebService {

    List<TeaGoods> getGoodsByAddtime(String goodsName);

    TeaGoods getGoodsDetail(Integer id);

    void saveOrder(TeaGoodsDTO teaGoodsDTO, Integer userId, String userName);

    List<TeaOrderVO> getOrderList(Integer userId);

    List<TeaIntegral> getIntegralList(Integer userId);

    BigDecimal getIntegralSum(Integer userId);
}
