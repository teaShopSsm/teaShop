package com.teaShop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.Message;
import com.teaShop.bean.SysRole;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.SysUserRole;
import com.teaShop.service.SysRoleService;
import com.teaShop.service.SysUserRoleService;
import com.teaShop.service.SysUserService;
import com.teaShop.utils.Const;
import com.teaShop.utils.MD5Util;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teaShop.service.UserService;

@Controller
public class LoginController {

	//@Autowired
	//private UserService service;
	@Autowired
	private SysUserRoleService userRoleService;
	
	@RequestMapping("/loginOut")
	public String logOut(HttpSession session){
		session.removeAttribute("username");
		return "../../login";
	}
	
//	@RequestMapping("/login1")
//	public void login(HttpServletRequest request, HttpServletResponse response){
//
//		try {
//			String username = request.getParameter("username") ;
//			String password = request.getParameter("password") ;
//			int isTrue = service.login(username, password);
//			if(isTrue >= 1){
//				HttpSession session = request.getSession();
//				session.setAttribute("username", username);
//			}
//			response.getWriter().print(isTrue);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private SysUserService service;

	MD5Util md5 = new MD5Util();
	//final static Logger logger = LoggerFactory.getLogger(SysUserController.class);
	Message message = null;

	public SysUserService getServices() {
		return service;
	}

	@Autowired
	public void setServices(SysUserService service) {
		this.service = service;
	}

	/**
	 * 用户登录
	 *
	 * @param request
	 *            返回json数据
	 * @return
	 */
	@RequestMapping(value = "/login1", produces = "text/html;charset=UTF-8")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("username").trim();
			String password = request.getParameter("password").trim();
			password = MD5Util.getMd5(password);
			boolean isLogin = false;
			message = new Message();
			if (name == null || name.equals("") || password == null || password.equals("")) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.NULL_NAME_OR_PASSWORD);
			} else {
				List<SysUser> userList = service.getUserByUserName(name);
				if (userList == null || userList.size() == 0) {
					message.setFlag(Const.NOT_OK);
					message.setMessage(Const.NAME_PASSWORD_MESSAGE);
				} else {
					List<SysUserRole> userRoleList = null;
					for (SysUser user : userList) {
						if (user.getPassword().equals(password)) {
							HttpSession session = request.getSession();
							userRoleList = userRoleService.getUserRoleByUserId(user.getUserId());
							session.setAttribute("user", user);
							isLogin = true;
							break;
						}
					}
					if (isLogin == true) {
						if(1 == userRoleList.get(0).getRoleId() || 2 == userRoleList.get(0).getRoleId()){
							message.setFlag(Const.OK);
						}else if(3 == userRoleList.get(0).getRoleId()){
							message.setFlag(Const.OKGK);
						}else {
							message.setFlag(Const.OKGK);
						}

						message.setMessage(Const.NORMAL);
						//logger.info(name + ":登录成功");
					} else {
						message.setFlag(Const.NOT_OK);
						message.setMessage(Const.NAME_PASSWORD_MESSAGE);
					}
				}
			}
			response.getWriter().println(JSONObject.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/register", produces = "text/html;charset=UTF-8")
	public void register(HttpServletRequest request, HttpServletResponse response) {
		try {
			message = new Message();
			HttpSession session = request.getSession();
			SysUser bean = (SysUser) session.getAttribute("user");
			SysUser user = new SysUser();
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String passwd = request.getParameter("passwd");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String desc = request.getParameter("desc");
			String userType = request.getParameter("userType");
			user.setUserName(userName);
			int count = service.getPhone(phone);
			if (count > 0) {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.EXIST_PHONE);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}

			if(service.getUserCount(userName) > 0){
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.EXIST_USERNAME);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			if(bean != null){
				if (bean.getIsSystem() == 1) {
					user.setIsSystem(2);
				} else if (bean.getIsSystem() == 2) {
					user.setIsSystem(3);
				} else {
					message.setFlag(Const.INSUFFICIENT_PERMISSIONS);
					message.setMessage(Const.INSUFFICIENT_PERMISSIONS_MESSAGE);
					response.getWriter().print(JSONArray.toJSON(message));
					return;
				}
				if (!md5.getMd5(password).equals(md5.getMd5(passwd))) {
					message.setFlag(-1);
					message.setMessage("两次密码不一样");
					response.getWriter().print(JSONArray.toJSON(message));
					return;
				}
			}else{
				user.setIsSystem(3);
			}
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone(phone);
			user.setDescription(desc);
			user.setCreateDate(new Date());
			user.setUserType(Integer.parseInt(userType));
			int result = service.addUser(user);
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(Const.INSERT_FAIL);
				message.setMessage(Const.INSERT_FAIL_MESSAGE);
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
