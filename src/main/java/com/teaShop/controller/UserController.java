package com.teaShop.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.User;
import com.teaShop.service.UserService;
import com.teaShop.utils.MyWebUtils;

@Controller
@RequestMapping("/User")
public class UserController {
	@Autowired
	private UserService service;
	
	@RequestMapping(value ="/queryManager" ,produces = "text/html;charset=UTF-8")
	public void queryManager(HttpServletRequest request, HttpServletResponse response) {
		try {
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			User user = new User();
			user.setPageNumber((pageNumber - 1) * pageSize);
			user.setPageSize(pageSize);
			response.getWriter().print(JSONArray.toJSON(service.queryManager(user)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="/confirmNewUser" ,produces = "text/html;charset=UTF-8")
	public void confirmNewUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			String UserName  = request.getParameter("UserName");
			String password  = request.getParameter("password");
			String name  = request.getParameter("name");
			String Email  = request.getParameter("Email");
			String Phone  = request.getParameter("Phone");
			String createDate= MyWebUtils.nowTime();
			User user = new User();
			user.setUserName(UserName);
			user.setPassword(password);
			user.setName(name);
			user.setEmail(Email);
			user.setPhone(Phone);
			user.setCreateDate(createDate);
			
			int result = service.confirmNewUser(user);
			response.getWriter().print(JSONArray.toJSON(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="/SelectUserInfo" ,produces = "text/html;charset=UTF-8")
	public void SelectUserInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Integer.parseInt(request.getParameter("user_id"));
			
			User user = service.SelectUserInfoByid(userId);
			response.getWriter().print(JSONArray.toJSON(user));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value ="/confirmmodifyUser" ,produces = "text/html;charset=UTF-8")
	public void confirmmodifyUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String UserName  = request.getParameter("UserName");
			String name  = request.getParameter("name");
			String Email  = request.getParameter("Email");
			String Phone  = request.getParameter("Phone");
			User user = new User();
			user.setUserId(userId);
			user.setUserName(UserName);
			user.setName(name);
			user.setEmail(Email);
			user.setPhone(Phone);
			int result = service.confirmmodifyUser(user);
			response.getWriter().print(JSONArray.toJSON(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="/deleteUser" ,produces = "text/html;charset=UTF-8")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
			int result = service.deleteUser(userId);
			response.getWriter().print(JSONArray.toJSON(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="/isHave" ,produces = "text/html;charset=UTF-8")
	public void isHave(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String username = request.getParameter("username");
			response.getWriter().print(service.isHave(username));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
