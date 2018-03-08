package com.teaShop.dao;

import com.teaShop.bean.SysUserPage;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysUserPageDao{

	/**
	 * 根据用户id 获取用户绑定的菜单信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysUserPage> getUserMenus(int userId);
	/**
	 * 获取用户角色绑定的菜单
	 * @param userId
	 * @return
	 */
	public List<SysUserPage> getUserRoleMenus(int userId);

	/**
	 * 绑定用户菜单
	 * 
	 * @param bean
	 * @return
	 */
	public int addUserPage(SysUserPage bean);

	/**
	 * 解绑用户菜单
	 * 
	 * @param userPageId
	 * @return
	 */
	public int deleteUserPage(SysUserPage bean);

	/**
	 * 获取页面是否在用户中
	 * 
	 * @param bean
	 * @return
	 */
	public Integer getUserPage(SysUserPage bean);

	/**
	 * 删除页面
	 * 
	 * @param bean
	 * @return
	 */
	public int deletePage(SysUserPage bean);
	/**
	 * 获取用户对应的 手机菜单
	 * @param id
	 * @return
	 */
	public List<SysUserPage> getMobilePage(int id);
	

}
