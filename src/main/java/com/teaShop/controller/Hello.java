package com.teaShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello {

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/cont")
	public String h() {
		return "hello";
	}
}
