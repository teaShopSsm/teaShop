package com.teaShop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teaShop.bean.ColdStorageTenant;
import com.teaShop.service.ColdStorageTenantService;

@Controller
@RequestMapping("/tenant")
public class ColdStorageTenantController {

	@Autowired
	private ColdStorageTenantService service;
	
	@RequestMapping("/getAll")
	public void getAll(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10")int pageSize){
		try {
			PageHelper.startPage(pageNumber, pageSize);
			List<ColdStorageTenant> cstList = service.getAll();
			PageInfo page = new PageInfo(cstList); 
			response.getWriter().print(JSONObject.toJSON(page));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/isRepeat")
	public void isRepeat(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("rentId")String rentId){
		try {
			response.getWriter().print(service.isRepeat(rentId));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/addTenant")
	public void addTenant(HttpServletResponse response,ColdStorageTenant cst){
		try {
			response.getWriter().print(service.addTenant(cst));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getOneByRenterId")
	public void getOneByRenterId(HttpServletResponse response,String renterId){
		try {
			response.getWriter().print(JSONObject.toJSON(service.getOneByRenterId(renterId)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/editTenant")
	public void editTenant(HttpServletResponse response,ColdStorageTenant cst){
		try {
			System.out.println(cst.toString());
			response.getWriter().print(JSONObject.toJSON(service.editTenant(cst)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
