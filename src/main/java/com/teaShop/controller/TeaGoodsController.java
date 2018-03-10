package com.teaShop.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.TeaGoods;
import com.teaShop.service.TeaGoodsService;
import com.teaShop.utils.Const;
import com.teaShop.utils.FileUpload;
import com.teaShop.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/teaGoods")
public class TeaGoodsController {

    private Message message = null;

    @Autowired
    private TeaGoodsService service;

    @RequestMapping("/getAll")
    public void queryAll(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                         @RequestParam(value="pageSize",defaultValue = "10") int pageSize){
        try{
            String goodsName = request.getParameter("goodsName");
            String goodsDesc = request.getParameter("goodsDesc");
            TeaGoods teaGoods = new TeaGoods();
            teaGoods.setGoodsname(goodsName);
            teaGoods.setBrief(goodsDesc);
            PageHelper.startPage(pageNum,pageSize);
            List<TeaGoods> list = service.getGoodsByAddtime(teaGoods);
            PageInfo info = new PageInfo(list);
            response.getWriter().print(JSONObject.toJSON(info));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/addTeaGoods")
    public void addTeaGoods(HttpServletRequest request, HttpServletResponse response,
                            TeaGoods teaGoods){
        try{
            HttpSession session = request.getSession();
            SysUser user = (SysUser)session.getAttribute("user");
            teaGoods.setAppuserid(String.valueOf(user.getUserId()));
            teaGoods.setAppuser(user.getUserName());
            teaGoods.setStatus(1);
            teaGoods.setAddtime(new Date());
            int flag = service.addGoods(teaGoods);
            message = new Message();

            if(flag > 0){
                message.setFlag(Const.OK);
            }else{
                message.setFlag(Const.INSERT_FAIL);
                message.setMessage(Const.INSERT_FAIL_MESSAGE);
            }
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getOneById")
    public void getOneById(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value="id")int id){
        try{
            TeaGoods teaGoods = service.getGoodsByID(id);
            message = new Message();
            if(teaGoods == null){
                message.setFlag(Const.NOT_OK);
                message.setMessage(Const.NULL_DATA_MESSAGE);
            }else{
                message.setFlag(Const.OK);
                message.setData(teaGoods);
            }
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/editGoods")
    public void updateTeaGoods(HttpServletRequest request, HttpServletResponse response,
                               TeaGoods teaGoods){
        try{
            HttpSession session = request.getSession();
            SysUser user = (SysUser)session.getAttribute("user");
            teaGoods.setAppuserid(String.valueOf(user.getUserId()));
            teaGoods.setAppuser(user.getUserName());
            teaGoods.setStatus(1);
            teaGoods.setAddtime(new Date());

            int flag = service.updateGoods(teaGoods);
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

    @RequestMapping("/deleteTeaGoods")
    public void deleteTeaGoods(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value="id")int id){
        try{
            message = new Message();
            if(service.deleteGoods(id) > 0){
                message.setFlag(Const.OK);
            }else{
                message.setFlag(Const.NOT_OK);
                message.setMessage(Const.DELETE_FAIL_MESSAGE);
            }
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response,MultipartFile pic){
        try{
            message = new Message();
            FileUpload fu = new FileUpload();
            message.setFlag(0);
            message.setMessage("上传成功");
            message.setData(fu.uploadPhoto(request,response,pic));
            response.getWriter().print(JSONObject.toJSON(message));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
