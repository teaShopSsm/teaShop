package com.teaShop.utils;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;



public class MyWebUtils {
	private static boolean isAjax = false;
	
	public static String nowTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static boolean isAjax(ServletRequest request) {
		HttpServletRequest request1 = (HttpServletRequest) request;
		String header = request1.getHeader("X-Requested-With");
		if(header.equalsIgnoreCase("XMLHttpRequest")){
			isAjax = true;
			return isAjax;
		}
		return isAjax;
	}
	
	public static void out(ServletResponse response, Object obj){
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		PrintWriter out = null;
		try {
			httpResponse.setCharacterEncoding("UTF-8");
			out = httpResponse.getWriter();
			out.print(JSONObject.toJSON(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
