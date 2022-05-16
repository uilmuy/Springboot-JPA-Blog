package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//ORM -<java objet를 테이블로 맵핑해 주는 (기술)것
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder//빌더 패턴
@Entity//Use 클래스가 자동으로 mysql에 테이블 생성
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링 전략 - 해당 프로젝트에서 연결된 DB의 넘버링 전략을 따라 간다.(auto_increment)
	private int id;//시퀀스, auto_increment
	
	@Column(nullable = false,length = 30)//nullable=false는 null 값을 허용하지 않는다.
	private String username; //아이디
	
	@Column(nullable = false,length = 100)// password 를 나중에 해쉬로 변경에 암호화 하겠음
	private String password;
	
	@Column(nullable = false,length = 50)
	private String email;
	
	@ColumnDefault("'user'") //디폴트 값에 문자를 넣을때는 "'"를 사용하여 문자임을 명시한다.
	private String role;  //Enum을 쓰는게 좋다. (admin,user,manager등 권한을 줄 수 있다.)
	
	@CreationTimestamp //시간 자동 입력
	private Timestamp createDate;
	
}
