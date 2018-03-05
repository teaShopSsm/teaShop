package com.teaShop.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.teaShop.bean.ColdStorageTenant;

@Repository
public interface ColdStorageTenantMapper {
	
	//查询所有租户信息
	public List<ColdStorageTenant> getAll();
	
	//查询用户id是否存在
	public Integer isRepeat(String rentId);
	
	//新增
	public Integer addTenant(ColdStorageTenant cst);
	
	//获取修改人员的信息
	public ColdStorageTenant getOneByRenterId(String renterId);
	
	//修改租户信息
	public Integer editTenant(ColdStorageTenant cst);
}