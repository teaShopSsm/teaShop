package com.teaShop.dao;

import com.teaShop.bean.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleDao {

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

}
