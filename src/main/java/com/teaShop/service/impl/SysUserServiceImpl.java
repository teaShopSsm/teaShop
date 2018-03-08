package com.teaShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.dao.SysUserDao;
import com.teaShop.bean.SysUser;
import com.teaShop.global.GlobalPara;
import com.teaShop.service.SysUserService;

@Service("userService")
@Transactional
public class SysUserServiceImpl implements SysUserService {
	private SysUserDao mapper;

	public SysUserDao getMapper() {
		return mapper;
	}

	@Autowired
	public void setMapper(SysUserDao mapper) {
		this.mapper = mapper;
	}

	public SysUser getUser(String name) {
		return mapper.getUser(name);
	}

	public JSONObject blurryUser(SysUser user) {
		List<SysUser> users = mapper.blurryUser(user);
		JSONObject obj = new JSONObject();
		if (!(users == null || users.size() < 1)) {
			int count = mapper.blurryUserCount(user);
			obj.put(GlobalPara.rows, users);
			obj.put(GlobalPara.total, count);
		} else {
			obj.put(GlobalPara.rows, users);
			obj.put(GlobalPara.total, 0);
		}
		return obj;
	}

	public synchronized int addUser(SysUser user) {

		mapper.addUser(user);
		return mapper.addUserRole(user);
	}

	public int deleteUser(int userId) {
		mapper.deleteUserRoles(userId);
		mapper.deleteUserMenus(userId);
		return mapper.deleteUser(userId);
	}

	public int getUserByNameOrPhone(SysUser user) {

		return mapper.getUserByNameOrPhone(user);
	}

	public int resetUserPassword(SysUser user) {

		return mapper.resetUserPassword(user);
	}

	public int updateUser(SysUser user) {

		return mapper.updateUser(user);
	}

	public SysUser getUserById(int id) {

		return mapper.getUserById(id);
	}

	public List<SysUser> getUserByUserName(String name) {
		return mapper.getUserByUserName(name);
	}

	/**
	 * 根据电话获取用户
	 * 
	 * @param phone
	 * @return
	 */
	public Integer getUserCountByPhone(String phone) {
		return mapper.getUserCountByPhone(phone);
	}

	/**
	 * 插入手机验证码
	 * 
	 * @param user
	 * @return
	 */
	public Integer updateIdentifyCode(SysUser user) {
		return mapper.updateIdentifyCode(user);
	}

	/**
	 * 根据电话获取用户
	 * 
	 * @param phone
	 * @return
	 */
	public List<SysUser> getUserByPhone(String phone) {
		return mapper.getUserByPhone(phone);
	}

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	public Integer changePassword(SysUser user) {
		return mapper.changePassword(user);
	}

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	public Integer registUser(SysUser user) {
		return mapper.registUser(user);
	}

	public int getPhone(String phone) {
		return mapper.getPhone(phone);
	}

	public List<String> getMailList() {
		return mapper.getMailList();
	}
}
