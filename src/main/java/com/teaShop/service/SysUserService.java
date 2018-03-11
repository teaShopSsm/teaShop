package com.teaShop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysUser;

public interface SysUserService {
	/**
	 * 
	 * @return
	 */
	public abstract SysUser getUser(String name);

	/**
	 * 获取全部用户信息
	 * 
	 * @return
	 */
	public JSONObject blurryUser(SysUser user);

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
	 * @return
	 */
	public int deleteUser(int userId);

	/**
	 * 根据用户名和电话获取用户
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
	 * @return
	 */
	public List<SysUser> getUserByUserName(String name);

	/**
	 * 根据电话获取用户
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

	public List<String> getMailList();
	public int getUserCount(String username);

	/**
	 * 获取全部会员信息
	 *
	 * @return
	 */
	//public JSONObject getAllMember(SysUser user);
	public List<SysUser> getAllMember(SysUser user);


	public Integer forbidMember(Integer userid);
}
