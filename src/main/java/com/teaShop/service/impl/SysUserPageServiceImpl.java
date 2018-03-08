package com.teaShop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.dao.SysPageDao;
import com.teaShop.dao.SysRoleDao;
import com.teaShop.dao.SysUserPageDao;
import com.teaShop.dao.SysUserRoleDao;
import com.teaShop.bean.SysPage;
import com.teaShop.bean.SysRole;
import com.teaShop.bean.SysUser;
import com.teaShop.bean.SysUserPage;
import com.teaShop.service.SysUserPageService;
import com.teaShop.utils.UserPageUtils;

@Service("userPageService")
public class SysUserPageServiceImpl implements SysUserPageService {

	private SysUserPageDao mapper;
	private SysPageDao pageMapper;
	private SysUserRoleDao userRoleMapper;
	private SysRoleDao roleMapper;

	public SysUserPageDao getMapper() {
		return mapper;
	}

	@Autowired
	public void setMapper(SysUserPageDao mapper) {
		this.mapper = mapper;
	}

	public SysPageDao getPageMapper() {
		return pageMapper;
	}

	@Autowired
	public void setPageMapper(SysPageDao pageMapper) {
		this.pageMapper = pageMapper;
	}

	public SysUserRoleDao getUserRoleMapper() {
		return userRoleMapper;
	}

	@Autowired
	public void setUserRoleMapper(SysUserRoleDao userRoleMapper) {
		this.userRoleMapper = userRoleMapper;
	}

	public SysRoleDao getRoleMapper() {
		return roleMapper;
	}

	@Autowired
	public void setRoleMapper(SysRoleDao roleMapper) {
		this.roleMapper = roleMapper;
	}

	/**
	 * 用户登录 获取菜单
	 */
	public List<SysPage> getMenus(SysUser user) {
		List<SysUserPage> userPage = mapper.getUserMenus(user.getUserId());
		List<SysUserPage> rolePage = mapper.getUserRoleMenus(user.getUserId());
		List<String> levelCodes = new ArrayList<String>();
		for (SysUserPage page : userPage) {
			String levelCode = page.getLevelCode();
			if ("".equals(levelCode) || levelCode.length() % 2 != 0) {
				continue;
			}
			int length = levelCode.length();
			while (length > 0) {
				String code = levelCode.substring(0, length);
				if (levelCodes.indexOf(code) == -1) {
					levelCodes.add(code);
				}
				length -= 2;
			}

		}
		for (SysUserPage page : rolePage) {
			if (page == null) {
				continue;
			}
			String levelCode = page.getLevelCode();
			if ("".equals(levelCode) || levelCode.length() % 2 != 0) {
				continue;
			}
			int length = levelCode.length();
			while (length > 0) {
				String code = levelCode.substring(0, length);
				if (levelCodes.indexOf(code) == -1) {
					levelCodes.add(code);
				}
				length -= 2;
			}
		}
		if (levelCodes.size() != 0) {
			List<SysPage> page = pageMapper.getPageByLevelcode(levelCodes);
			return page;
		} else {
			return null;
		}
	}

	/**
	 * 获取用户菜单信息
	 */
	public List<SysUserPage> getUserMenus(int userId) {
		List<SysPage> pages = pageMapper.getUserPage();
		List<SysUserPage> userPage = mapper.getUserMenus(userId);
		return UserPageUtils.initMenus(pages, userPage);
	}

	public int addUserPage(SysUserPage bean) {
		return mapper.addUserPage(bean);
	}

	public int deleteUserPage(SysUserPage bean) {
		return mapper.deleteUserPage(bean);
	}

	public JSONObject getPageUsing(SysUserPage bean) {
		JSONObject object = new JSONObject();
		SysRole sysRole = new SysRole();
		SysPage sysPage = new SysPage();
		sysRole.setPageId(bean.getPageId());
		sysPage.setPageId(bean.getPageId());
		Integer rolePage = roleMapper.getRolePage(sysRole);
		if (rolePage != null && rolePage > 0) {
			object.put("flag", -1);
			object.put("message", "该页面在角色使用中");
			return object;
		}

		Integer userPage = mapper.getUserPage(bean);
		if (userPage != null && userPage > 0) {
			object.put("flag", -1);
			object.put("message", "该页面在用户使用中");
			return object;
		}

		Integer userPrePage = pageMapper.getUserPageByPrePageId(sysPage);
		Integer rolePrePage = pageMapper.getRolePageByPrePageId(sysPage);

		if ((userPrePage != null && userPrePage > 0) || (rolePrePage != null && rolePrePage > 0)) {
			object.put("flag", -1);
			object.put("message", "该页面的子页面在用户使用中");
			return object;
		}

		int result = mapper.deletePage(bean);
		if (result == 0) {
			object.put("flag", -1);
			object.put("message", "删除页面失败");
		} else {
			object.put("flag", 0);
			object.put("message", "删除页面成功");
		}
		return object;
	}

	/**
	 * 获取用户对应的手机菜单
	 * 
	 * @param id
	 * @return
	 */
	public List<SysUserPage> getMobilePage(int id) {
		return mapper.getMobilePage(id);
	}
}
