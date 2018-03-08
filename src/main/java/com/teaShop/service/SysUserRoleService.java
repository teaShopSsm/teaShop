package com.teaShop.service;

import java.util.List;

import com.teaShop.bean.SysUserRole;

public interface SysUserRoleService {
	/**
	 * 根据用户id 获取用户绑定的角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> getUserRole(int userId);

	/**
	 * 绑定用户角色
	 * 
	 * @param bean
	 * @return
	 */
	public int addUserRole(SysUserRole bean);

	/**
	 * 解绑用户角色
	 * 
	 * @param userPageId
	 * @return
	 */
	public int deleteUserRole(SysUserRole bean);

	/**
	 *
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> getUserRoleByUserId(int userId);
}
