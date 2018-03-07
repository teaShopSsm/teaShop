package com.teaShop.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teaShop.bean.ColdStorageTenant;
import com.teaShop.bean.TeaOrders;
import com.teaShop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @RequestMapping("/getAll")
    public void getAll(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize",defaultValue="10")int pageSize){
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<ColdStorageTenant> cstList = ordersService.getAll();
            PageInfo page = new PageInfo(cstList);
            response.getWriter().print(JSONObject.toJSON(page));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
