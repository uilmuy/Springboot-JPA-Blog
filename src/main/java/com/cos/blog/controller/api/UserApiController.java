package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	//@Autowired
	//private HttpSession session; //일반 로그인시 사용
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		
		System.out.println("UserApiController : save 호출됨");
		
		//String encPassword = encoder.encode(user.getPassword());
		//user.setRole(RoleType.USER);
		//user.setPassword(encPassword);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	
	}
	
	//스프링 시큐리티를 이용해서 로그인 
	
	// 일반 로그인 세션 사용
	/*
	  @PostMapping("api/login") public ResponseDto<Integer> login(@RequestBody User user) { //HttpSession session 인자 값으로 넣어 세션을 활성화 할수도 있지만 @Autowired로 di 할수도있다.
	 	  
	  System.out.println("UserApiController : Login 호출됨"); User principal =
	  userService.로그인(user); //접근 주체
	  
	  if(principal !=null) { session.setAttribute("principal",principal); }
	  
	  return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	  
	  }
	 */
	
	
	
} 