package com.teaShop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.teaShop.bean.User;


@Repository
public interface UserMapper {
	
	public List<User> QueryManager(User user);
	
	public int QueryUserCount();
	
	public int  confirmNewUser(User user);
	
	public User SelectUserInfoByid(int userId);
	
	public int confirmmodifyUser(User user);
	
	public int deleteUser(int userId);
	
	//查询是否存在输入的账号
	public Integer isHave(String username);
	
	//验证账号密码是否存在
	public Integer login(@Param("username")String username, @Param("password")String password);
    
}