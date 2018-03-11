package com.teaShop.controller;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.TeaGoods;
import com.teaShop.bean.TeaIntegral;
import com.teaShop.bean.dto.TeaGoodsDTO;
import com.teaShop.bean.dto.TeaOrderVO;
import com.teaShop.service.TeaGoodsWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/web")
public class WebController {
    @Autowired
    private TeaGoodsWebService teaWebService;

    @RequestMapping("getGoodsByAddtime")
    public List<TeaGoods> getGoodsByAddtime(@RequestParam("goodsName") String goodsName) {
        try {
            return teaWebService.getGoodsByAddtime(goodsName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("getGoodsDetail/{goodsId}")
    public TeaGoods getGoodsDetail(@PathVariable("goodsId") Integer goodsId) {
        try {
            return teaWebService.getGoodsDetail(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/saveOrder")
    public JSONObject saveOrder(TeaGoodsDTO teaGoodsDTO, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            SysUser user = (SysUser) session.getAttribute("user");
            teaWebService.saveOrder(teaGoodsDTO, user.getUserId(), user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject data = new JSONObject();
        return data;
    }

    @RequestMapping("/getUserInfo")
    public SysUser getUserInfo(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            SysUser user = (SysUser) session.getAttribute("user");
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getOrderList")
    public List<TeaOrderVO> getOrderList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        return teaWebService.getOrderList(user.getUserId());
    }

    @RequestMapping("/getIntegralList")
    public List<TeaIntegral> getIntegralList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        return teaWebService.getIntegralList(user.getUserId());
    }

    @RequestMapping("/getIntegralSum")
    public BigDecimal getIntegralSum(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        return teaWebService.getIntegralSum(user.getUserId());
    }
}
