package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입 할 수 있는 경로를 /ayth 라는 경로 이하로만 허용 하겠다
//그냥 주소가 "/"이면 index.jsp 허용
//static 이하에 있는 것들 허용.js . img 등
@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {	
		return "user/loginForm";
	}
	
	
}
