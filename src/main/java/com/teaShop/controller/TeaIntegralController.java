package com.teaShop.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.TeaGoods;
import com.teaShop.bean.TeaIntegral;
import com.teaShop.service.OrdersService;
import com.teaShop.service.SysUserService;
import com.teaShop.service.TeaIntegralService;
import com.teaShop.service.impl.OrdersServiceImpl;
import com.teaShop.utils.Const;
import com.teaShop.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/teaIntegral")
public class TeaIntegralController {

    private Message message;

    @Autowired
    private TeaIntegralService service;
    @Autowired
    private SysUserService userService;

    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/getAll")
    public void getAll(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                       @RequestParam(value="pageSize",defaultValue = "10") int pageSize){
        try{
            String username = request.getParameter("username");
            TeaIntegral teaIntegral = new TeaIntegral();
            teaIntegral.setUsername(username);
            PageHelper.startPage(pageNum,pageSize);
            List<TeaIntegral> list = service.getAll(teaIntegral);
            PageInfo info = new PageInfo(list);
            response.getWriter().print(JSONObject.toJSON(info));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getOneById")
    public void getOneById(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="id")int id){
        try{
            TeaIntegral teaIntegral = service.getOneById(id);
            message = new Message();
            if(teaIntegral == null){
                message.setFlag(Const.NOT_OK);
                message.setMessage(Const.NULL_DATA_MESSAGE);
            }else{
                message.setFlag(Const.OK);
                message.setData(teaIntegral);
            }
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/editIntegral")
    public void updateTeaGoods(HttpServletRequest request, HttpServletResponse response,
                               TeaIntegral teaIntegral){
        try{
            Integer userId = userService.getUserIdByUserName(teaIntegral.getUsername());
            teaIntegral.setUserid(userId);
            int flag = service.updateIntegral(teaIntegral);
            message = new Message();

            if(flag > 0){
                message.setFlag(Const.OK);
            }else{
                message.setFlag(Const.UPDATE_FAIL);
                message.setMessage(Const.UPDATE_FAIL_MESSAGE);
            }
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/isHave")
    public void isHave(HttpServletRequest request, HttpServletResponse response){
        try{
            String username = request.getParameter("username");
            int flag = userService.getUserIsHuiYuan(username);
            System.out.println("flag:" + flag);
            message = new Message();

            if(flag > 0){
                response.getWriter().print(0);
            }else{
                response.getWriter().print(1);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/orderIsHave")
    public void orderIsHave(HttpServletRequest request, HttpServletResponse response,@RequestParam("orderid")int orderid){
        try{
            int flag = ordersService.orderIsHave(orderid);
            message = new Message();

            if(flag > 0){
                response.getWriter().print(0);
            }else{
                response.getWriter().print(1);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
