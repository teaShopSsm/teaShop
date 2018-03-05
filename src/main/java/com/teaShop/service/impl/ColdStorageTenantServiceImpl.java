package com.teaShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teaShop.bean.ColdStorageTenant;
import com.teaShop.dao.ColdStorageTenantMapper;
import com.teaShop.service.ColdStorageTenantService;

@Service
public class ColdStorageTenantServiceImpl implements ColdStorageTenantService {

	@Autowired
	private ColdStorageTenantMapper mapper;
	
	public List<ColdStorageTenant> getAll() {
		// TODO Auto-generated method stub
		return mapper.getAll();
	}

	public Integer isRepeat(String rentId) {
		// TODO Auto-generated method stub
		return mapper.isRepeat(rentId);
	}

	public Integer addTenant(ColdStorageTenant cst) {
		// TODO Auto-generated method stub
		return mapper.addTenant(cst);
	}

	public ColdStorageTenant getOneByRenterId(String renterId) {
		// TODO Auto-generated method stub
		return mapper.getOneByRenterId(renterId);
	}

	public Integer editTenant(ColdStorageTenant cst) {
		// TODO Auto-generated method stub
		return mapper.editTenant(cst);
	}
	
	

}
