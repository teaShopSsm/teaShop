package com.teaShop.controller;

import com.teaShop.bean.TeaGoods;
import com.teaShop.service.TeaGoodsWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/web")
public class WebController {
    @Autowired
    private TeaGoodsWebService teaWebService;

    @RequestMapping("getGoodsByAddtime")
    public List<TeaGoods> getGoodsByAddtime(HttpServletRequest request, HttpServletResponse response){
        try {
            teaWebService.getGoodsByAddtime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
