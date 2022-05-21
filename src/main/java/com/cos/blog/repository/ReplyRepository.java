package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Reply;

//DAO
//자동으로 빈으로 등록 된다.
//@Repository 생략가능 하다.
public interface ReplyRepository extends JpaRepository<Reply, Integer>{ //extends 해당 jparepository는 User테이블이 관리하는 레포지토리이고 user테이블 pkey는 integer야
	
}
