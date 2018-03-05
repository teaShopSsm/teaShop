package com.teaShop.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginInterceptor implements HandlerInterceptor {
	private static final String LOGIN_URL = "/login1"; 
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
//		String requestURI = request.getRequestURI();  
//        if(requestURI.indexOf("editClientIfo.action")>0){  
//            //说明处在编辑的页面  
//            HttpSession session = request.getSession();  
//            String username = (String) session.getAttribute("user");  
//            if(username!=null){  
//                //登陆成功的用户  
//                return true;  
//            }else{  
//               //没有登陆，转向登陆界面  
//                request.getRequestDispatcher("/login.jsp").forward(request,arg1);  
//              return false;  
//            }  
//        }else{  
//            return true;  
//        } 
//		 if ("GET".equalsIgnoreCase(request.getMethod())) {
//             RequestUtil.saveRequest();
//	     }
	     String requestUri = request.getRequestURI();
	     String contextPath = request.getContextPath();
	     String url = requestUri.substring(contextPath.length());        
	     if ("/login1".equals(url) || "/User/isHave".equals(url)) {                  
	             return true;
	     }else {               
	             //Integer user =  (Integer)request.getSession().getAttribute("User"); 
	             String username = (String)request.getSession().getAttribute("username");
	            // String password = (String)request.getSession().getAttribute("password");
	            if(username!=null){  
	                //登陆成功的用户  
	                return true;  
	            }else{  
	               //没有登陆，转向登陆界面  
	              request.getRequestDispatcher("/login.html").forward(request,arg1);  
	              return false;  
	            }  
	    }
	}

}
