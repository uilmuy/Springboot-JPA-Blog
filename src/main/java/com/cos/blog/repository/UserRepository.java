package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//자동으로 빈으로 등록 된다.
//@Repository 생략가능 하다.
public interface UserRepository extends JpaRepository<User, Integer>{ //extends 해당 jparepository는 User테이블이 관리하는 레포지토리이고 user테이블 pkey는 integer야

	Optional<User> findByUsername(String username);
	
	
	//Jpa naming 쿼리 전략
	//아래처럼 함수를 JPA 네이밍 전략을 사용하면 "SELECT * FROM user WHERE username=? AND password=?;" 라고 쿠커리가 동작
	
	//User findByUsernameAndPassword(String username, String password);
	
	//위의 형태를 아래처럼 사용 할 수 있다.
	//@Query(value = "SELECT * FROM user WHERE username=? AND password=?",nativeQuery = true)
	//User login (String username, String password);
	
}
