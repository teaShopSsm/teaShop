package com.teaShop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysPage;
import com.teaShop.service.SysPageService;
import com.teaShop.utils.Const;
import com.teaShop.vo.Message;

@Controller
@RequestMapping("/page")
public class SysPageController {
	@Autowired
	private SysPageService service;

	Message message = null;

	/**
	 * 模糊查询部门信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/blurryPage", produces = "text/html;charset=UTF-8")
	public void blurryPage(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String pageName = request.getParameter("pageName");
			String pageDesc = request.getParameter("pageDesc");
			SysPage page = new SysPage();
			page.setPageName(pageName);
			page.setDescription(pageDesc);
			List<SysPage> pages = service.blurryPage(page);
			for(SysPage sp : pages){
				sp.setIcon("<i class='"+sp.getIcon()+"'></i>  ");
			}
			message = new Message();
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
	 * 根据page_id查询prepageid
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPrePageIdById")
	public void getPrePageIdById(HttpServletRequest request, HttpServletResponse response){
		try {
			int pageId = Integer.parseInt(request.getParameter("pageId"));
			message = new Message();
			message.setFlag(Const.OK);
			message.setMessage(Const.NORMAL);
			message.setData(service.getPrePageIdById(pageId));
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 模糊查询部门信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addPage")
	public void addPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			message = new Message();
			String prePageId = request.getParameter("prePageId");
			String pageName = request.getParameter("pageName");
			if ("-1".equals(prePageId)) {
				prePageId = "0";
			}

			String url = request.getParameter("url");
			String pageTypeId = request.getParameter("pageTypeId");
			String pageIcon = request.getParameter("pageIcon");
			String pageOrder = request.getParameter("pageOrder");
			String pageDesc = request.getParameter("pageDesc");
			SysPage page = new SysPage();
			page.setPageName(pageName);
			if(prePageId != null && prePageId != ""  && !prePageId.equals("")){
				page.setPrePageId(Integer.parseInt(prePageId));
			}
			
			if (service.getPageByName(page) > 0) {
				message.setFlag(-1);
				message.setMessage("同一级菜单下存在相同名称的菜单");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			String levelCode = service.getLevelCodeByPrePageId(page);
			if(levelCode == null || "".equals(levelCode) || "" == levelCode){
				levelCode = service.getLevelCodeByPageId(page);
				page.setLevelCode(levelCode + "10");
			}else{
				page.setLevelCode(String.valueOf(Integer.parseInt(levelCode)+01));
			}
			page.setUrl(url);
			page.setIcon(pageIcon);
			page.setOrderCode(pageOrder);
			page.setDescription(pageDesc);
			//page.setPageTypeId(Integer.parseInt(pageTypeId));
			int result = service.addPage(page);
			message = new Message();
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(result);
				message.setMessage("添加失败");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有可以作为父级菜单的page
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllPrePage", produces = "text/html;charset=UTF-8")
	public void getAllPrePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<SysPage> pages = service.getAllPrePage();
			message = new Message();
			message.setFlag(Const.OK);
			message.setMessage(Const.NORMAL);
			message.setData(pages);
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/editPage")
	public void editPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pageId = request.getParameter("pageId");
			String pageName = request.getParameter("pageName");
			SysPage bean = new SysPage();
			bean.setPageId(Integer.parseInt(pageId));
			SysPage page = service.getPageById(bean);
			message = new Message();
			if (!page.getPageName().equals(pageName)) {
				page.setPageName(pageName);
				if (service.getPageByName(page) > 0) {
					message.setFlag(-1);
					message.setMessage("同一级菜单下存在相同名称的菜单");
					response.getWriter().print(JSONArray.toJSON(message));
					return;
				}
			}
			String prePageId = request.getParameter("prePageId");
			String url = request.getParameter("url");
			String icon = request.getParameter("pageIcon");
			String pageOrder = request.getParameter("pageOrder");
			String pageDesc = request.getParameter("pageDesc");
			page.setPageId(Integer.parseInt(pageId));
			if(prePageId != null && prePageId != "" && !prePageId.equals("")){
				
				page.setPrePageId(Integer.parseInt(prePageId));
			}
			page.setPageName(pageName);
			page.setUrl(url);
			page.setIcon(icon);
			page.setOrderCode(pageOrder);
			page.setDescription(pageDesc);
			JSONObject object = service.editPage(page);
			message.setFlag(object.getIntValue("flag"));
			message.setMessage(object.getString("message"));
			response.getWriter().print(JSONObject.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id获取
	 */
	@RequestMapping("/getPageById")
	public void getPageById(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("pageId"));
			SysPage bean = new SysPage();
			bean.setPageId(id);
			SysPage page = service.getPageById(bean);
			message = new Message();
			if (page == null) {
				message.setFlag(-1);
				message.setMessage("获取数据失败");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			} else {
				message.setFlag(0);
				message.setData(page);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
