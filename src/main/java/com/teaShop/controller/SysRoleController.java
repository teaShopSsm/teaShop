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
import com.teaShop.bean.SysRole;
import com.teaShop.service.SysRoleService;
import com.teaShop.utils.Const;
import com.teaShop.vo.Message;

@Controller
@RequestMapping("/role")
public class SysRoleController {
	private SysRoleService service;

	Message message = null;

	public SysRoleService getServices() {
		return service;
	}

	@Autowired
	public void setServices(SysRoleService service) {
		this.service = service;
	}

	/**
	 * 模糊查询 角色信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/blurryRole", produces = "text/html;charset=UTF-8")
	public void blurryRole(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleName = request.getParameter("roleName");
			String roleDesc = request.getParameter("roleDesc");
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			SysRole role = new SysRole();
			role.setRoleName(roleName);
			role.setDescription(roleDesc);
			role.setPageNumber((pageNumber - 1) * pageSize);
			role.setPageSize(pageSize);
			JSONObject obj = service.blurryRole(role);
			response.getWriter().print(JSONArray.toJSON(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addRole")
	public void addRole(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleName = request.getParameter("roleName");
			String roleDesc = request.getParameter("roleDesc");
			message = new Message();
			SysRole isExist = service.getRoleInfoByName(roleName);
			if (isExist != null) {
				message.setFlag(-1);
				message.setMessage("角色名称已经存在");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}

			SysRole role = new SysRole();
			role.setRoleName(roleName);
			role.setDescription(roleDesc);
			int result = service.addRole(role);

			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(-1);
				message.setMessage("添加失败");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getRoleInfo")
	public void getRoleInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			message = new Message();
			if (roleId == null || "".equals(roleId)) {
				message.setFlag(-1);
				message.setMessage("请选择角色");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			SysRole role = service.getRoleInfoById(Integer.parseInt(roleId));
			if (role == null) {
				message.setFlag(-1);
				message.setMessage("获取角色信息失败");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			} else {
				message.setFlag(0);
				message.setData(role);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateRoleInfo")
	public void updateRoleInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			String roleName = request.getParameter("roleName");
			String roleDesc = request.getParameter("roleDesc");
			message = new Message();
			if (roleId == null || "".equals(roleId)) {
				message.setFlag(-1);
				message.setMessage("请选择角色");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			SysRole role = service.getRoleInfoById(Integer.parseInt(roleId));
			if (role == null) {
				message.setFlag(-1);
				message.setMessage("获取角色信息失败");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
			if (role != null && !roleName.equals(role.getRoleName())) {
				SysRole isExist = service.getRoleInfoByName(roleName);
				if (isExist != null) {
					message.setFlag(-1);
					message.setMessage("角色名称已经存在");
					response.getWriter().print(JSONArray.toJSON(message));
					return;
				}
			}

			role.setRoleName(roleName);
			role.setDescription(roleDesc);
			int result = service.updateRoleInfo(role);
			message = new Message();
			if (result < 1) {
				message.setFlag(-1);
				message.setMessage("添加失败");
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			} else {
				message.setFlag(0);
				response.getWriter().print(JSONArray.toJSON(message));
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteRole")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			int count = service.getUserByrole(Integer.parseInt(roleId));
			message = new Message();
			if (count > 0) {
				message.setFlag(-1);
				message.setMessage("角色正在使用中,不能删除");
			} else {
				int result = service.deleteRole(Integer.parseInt(roleId));
				if (result == 1) {
					message.setFlag(0);
				} else {
					message.setFlag(-1);
					message.setMessage("删除失败");
				}
			}
			response.getWriter().print(JSONArray.toJSON(message));

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
	@RequestMapping("/getRoleMenus")
	public void getRoleMenus(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			SysRole role = new SysRole();
			role.setRoleId(Integer.parseInt(roleId));
			List<SysRole> rolelist = service.getRoleMenus(role);
			message = new Message();
			message.setFlag(Const.OK);
			message.setMessage(Const.NORMAL);
			message.setData(rolelist);
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加角色 页面 信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addRolePage")
	public void addRolePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			String pageId = request.getParameter("pageId");

			SysRole role = new SysRole();
			role.setRoleId(Integer.parseInt(roleId));
			role.setPageId(Integer.parseInt(pageId));
			int result = service.addRolePage(role);
			message = new Message();
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(-1);
				message.setMessage("添加失败");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除角色 页面 信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteRolePage")
	public void deleteRolePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			String pageId = request.getParameter("pageId");

			SysRole role = new SysRole();
			role.setRoleId(Integer.parseInt(roleId));
			role.setPageId(Integer.parseInt(pageId));
			int result = service.deleteRolePage(role);
			message = new Message();
			if (result == 1) {
				message.setFlag(0);
			} else {
				message.setFlag(-1);
				message.setMessage("添加失败");
			}
			response.getWriter().print(JSONArray.toJSON(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
