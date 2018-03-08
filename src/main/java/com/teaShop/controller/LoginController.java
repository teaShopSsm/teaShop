package com.teaShop.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
						if(1 == userRoleList.get(0).getRoleId() || 14 == userRoleList.get(0).getRoleId()){
							message.setFlag(Const.OK);
						}else if(13 == userRoleList.get(0).getRoleId()){
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
	
}
