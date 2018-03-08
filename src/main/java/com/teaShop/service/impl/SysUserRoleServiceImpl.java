package com.teaShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teaShop.dao.SysRoleDao;
import com.teaShop.dao.SysUserRoleDao;
import com.teaShop.bean.SysRole;
import com.teaShop.bean.SysUserRole;
import com.teaShop.service.SysUserRoleService;
import com.teaShop.utils.UserRoleUtils;

@Service("userRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

	private SysUserRoleDao mapper;
	private SysRoleDao roleMapper;

	public SysUserRoleDao getMapper() {
		return mapper;
	}

	@Autowired
	public void setMapper(SysUserRoleDao mapper) {
		this.mapper = mapper;
	}

	public SysRoleDao getRoleMapper() {
		return roleMapper;
	}

	@Autowired
	public void setRoleMapper(SysRoleDao roleMapper) {
		this.roleMapper = roleMapper;
	}

	public int addUserRole(SysUserRole bean) {
		return mapper.addUserRole(bean);
	}

	public int deleteUserRole(SysUserRole bean) {
		return mapper.deleteUserRole(bean);
	}

	public List<SysUserRole> getUserRole(int userId) {
		List<SysUserRole> userRoles = mapper.getUserRole(userId);
		List<SysRole> roles = roleMapper.getAllRole();
		return UserRoleUtils.initUserRole(roles, userRoles);
	}

}
