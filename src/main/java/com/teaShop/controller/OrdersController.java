package com.teaShop.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teaShop.bean.ColdStorageTenant;
import com.teaShop.bean.TeaIntegral;
import com.teaShop.bean.TeaOrders;
import com.teaShop.service.OrdersService;
import com.teaShop.service.SysUserService;
import com.teaShop.utils.Const;
import com.teaShop.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/teaOrders")
public class OrdersController {

    private Message message;
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private SysUserService userService;
    @RequestMapping("/getAll")
    public void getAll(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize",defaultValue="10")int pageSize,
                       @RequestParam(value="status",defaultValue = "1")int status){
        try {
            String username = request.getParameter("username");
            TeaOrders teaOrders = new TeaOrders();
            teaOrders.setUsername(username);
            teaOrders.setStatus(status);
            PageHelper.startPage(pageNumber, pageSize);
            List<TeaOrders> cstList = ordersService.getAll(teaOrders);
            PageInfo page = new PageInfo(cstList);
            response.getWriter().print(JSONObject.toJSON(page));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @RequestMapping("/getOneById")
    public void getOneById(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value="id",defaultValue = "0")int id){
        try{
            TeaOrders teaOrders = ordersService.getOneById(id);
            message = new Message();
            if(teaOrders == null){
                message.setFlag(Const.NOT_OK);
                message.setMessage(Const.NULL_DATA_MESSAGE);
            }else{
                message.setFlag(Const.OK);
                message.setData(teaOrders);
            }
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/editOrders")
    public void updateTeaGoods(HttpServletRequest request, HttpServletResponse response,
                               TeaOrders teaOrders){
        try{
            Integer userId = userService.getUserIdByUserName(teaOrders.getUsername());
            teaOrders.setUserid(userId);
            int flag = ordersService.editOrder(teaOrders);
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
    public void isHave(HttpServletRequest request, HttpServletResponse response,
                       String username){
        try{
            int flag = userService.getUserIsHuiYuan(username);
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
