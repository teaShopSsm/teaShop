package com.teaShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.User;
import com.teaShop.dao.UserMapper;
import com.teaShop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	public JSONObject queryManager(User user) {
		JSONObject obj = new JSONObject();
		List<User> userlist =  mapper.QueryManager(user);
		/*for (User user2 : userlist) {
			System.out.println(user2.getCreateDate());
		}*/
		int count = mapper.QueryUserCount();
		obj.put("rows", userlist);
		obj.put("total", count);
		return obj;
	}

	public int confirmNewUser(User user) {
		
		
		return mapper.confirmNewUser(user);
	}

	public User SelectUserInfoByid(int userId) {
		return mapper.SelectUserInfoByid(userId);
	}

	public int confirmmodifyUser(User user) {
		return mapper.confirmmodifyUser(user);
	}

	public int deleteUser(int userId) {
		return mapper.deleteUser(userId);
	}



	public Integer isHave(String username) {
		return mapper.isHave(username);
	}

	public Integer login(String username, String password) {
		// TODO Auto-generated method stub
		return mapper.login(username, password);
	}

}
