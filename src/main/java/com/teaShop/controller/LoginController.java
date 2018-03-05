package com.teaShop.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teaShop.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService service;
	
	@RequestMapping("/loginOut")
	public String logOut(HttpSession session){
		session.removeAttribute("username");
		return "../../login";
	}
	
	@RequestMapping("/login1")
	public void login(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String username = request.getParameter("username") ;
			String password = request.getParameter("password") ;
			int isTrue = service.login(username, password);
			if(isTrue >= 1){
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
			}
			response.getWriter().print(isTrue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
