package com.teaShop.service;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.User;

public interface UserService {

	public JSONObject queryManager(User user);
	
	public int  confirmNewUser(User user);
	
	public User SelectUserInfoByid(int userId);
	
	public int confirmmodifyUser(User user);
	
	public int deleteUser(int userId);
	
	public Integer isHave(String username);
	
	public Integer login(String username,String password);
}
