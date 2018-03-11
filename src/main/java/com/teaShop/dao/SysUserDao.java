package com.teaShop.dao;

import com.teaShop.bean.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao {

	/**
	 * 根据用户名获得用户信息
	 * 
	 * @param phone
	 * @return
	 */
	public SysUser getUser(String name);

	/**
	 * 获取全部用户信息
	 * 
	 * @return
	 */
	public List<SysUser> blurryUser(SysUser user);

	/**
	 * 满足条件的总个数
	 * 
	 * @param user
	 * @return
	 */
	public int blurryUserCount(SysUser user);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(SysUser user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUser(int userId);

	/**
	 * 根据用户名和电话获取 用户
	 * 
	 * @param user
	 * @return
	 */
	public int getUserByNameOrPhone(SysUser user);

	/**
	 * 重置密码
	 * 
	 * @param user
	 * @return
	 */
	public int resetUserPassword(SysUser user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(SysUser user);

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public SysUser getUserById(int id);

	/**
	 * 根据电话或邮箱获取用户
	 * 
	 * @param accounts
	 * @return
	 */
	public List<SysUser> getUserByUserName(String name);

	/**
	 * 根据电话获取用户个数
	 * 
	 * @param phone
	 * @return
	 */
	public Integer getUserCountByPhone(String phone);

	/**
	 * 插入手机验证码
	 * 
	 * @param user
	 * @return
	 */
	public Integer updateIdentifyCode(SysUser user);

	/**
	 * 根据电话获取用户
	 * 
	 * @param phone
	 * @return
	 */
	public List<SysUser> getUserByPhone(String phone);

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	public Integer changePassword(SysUser user);

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	public Integer registUser(SysUser user);

	public int getPhone(String phone);

	public int getUserCount(String username);

	public List<String> getMailList();

	public void deleteUserRoles(int userId);

	public void deleteUserMenus(int userId);

	// 添加用户时，选择用户类型，并向用户-角色表中插入记录
	public int addUserRole(SysUser user);

	/**
	 * 获取全部用户信息
	 *
	 * @return
	 */
	public List<SysUser> getAllMember(SysUser user);

	public int forbidMember(Integer userId);
}
