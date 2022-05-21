package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // 스프링이 컴포넌트 스캔을 통해 bean에 등록 해 준다.
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void 회원가입(User user) {

		userRepository.save(user);

	}
	
	@Transactional(readOnly = true)  //select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션이 종료 될때까지 정합성을 유지 할 수 있다.
	public User 로그인(User user) {
		
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	
	}
}
