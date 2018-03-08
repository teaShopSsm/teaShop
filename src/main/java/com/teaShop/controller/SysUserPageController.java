package com.teaShop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysPage;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.SysUserPage;
import com.teaShop.service.SysUserPageService;
import com.teaShop.utils.Const;
import com.teaShop.vo.Message;

@Controller
@RequestMapping("/userPage")
public class SysUserPageController {
	private SysUserPageService service;

	Message message = null;

	public SysUserPageService getServices() {
		return service;
	}

	@Autowired
	public void setServices(SysUserPageService service) {
		this.service = service;
	}

	/**
	 * 绑定用户菜单的时候获取菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getMenus", produces = "text/html;charset=UTF-8")
	public void getMenus(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			SysUser user = (SysUser) session.getAttribute("user");
			message = new Message();
			if (user != null) {
				List<SysPage> pages = service.getMenus(user);
				if (pages == null || pages.size() == 0) {
					message.setFlag(Const.NOT_OK);
					message.setMessage(Const.LOGIN_TIMEOUT);
				}else {
					message.setFlag(Const.OK);
					message.setMessage(Const.NORMAL);
					message.setData(pages);
				}

			} else {
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.LOGIN_TIMEOUT);
			}
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定用户菜单的时候获取菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserMenus")
	public void getUserMenus(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			
			List<SysUserPage> pages = service.getUserMenus(Integer.parseInt(userId));
			Message message = new Message();
			if (pages == null || pages.size() < 1) {
				
				message.setFlag(Const.NOT_OK);
				message.setMessage(Const.NULL_MESSAGE);
			} else {
				message.setFlag(Const.OK);
				message.setMessage(Const.NORMAL);
				message.setData(pages);
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addUserPage")
	public void addUserPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			String pageId = request.getParameter("pageId");
			SysUserPage bean = new SysUserPage();
			bean.setPageId(Integer.parseInt(pageId));
			bean.setUserId(Integer.parseInt(userId));
			int result = service.addUserPage(bean);
			message = new Message();
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(-1);
				message.setMessage("绑定失败");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteUserPage")
	public void deleteUserPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			String pageId = request.getParameter("pageId");
			SysUserPage bean = new SysUserPage();
			bean.setPageId(Integer.parseInt(pageId));
			bean.setUserId(Integer.parseInt(userId));
			int result = service.deleteUserPage(bean);
			message = new Message();
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(-1);
				message.setMessage("绑定失败");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deletePage", produces = "text/html;charset=UTF-8")
	public void deletePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pageId = request.getParameter("pageId");
			SysUserPage bean = new SysUserPage();
			bean.setPageId(Integer.parseInt(pageId));
			JSONObject object = service.getPageUsing(bean);
			message = new Message();
			message.setFlag(object.getIntValue("flag"));
			message.setMessage(object.getString("message"));
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
