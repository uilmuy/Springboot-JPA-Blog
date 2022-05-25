package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //내용이 길수 있어서 섬머노트라는 라이브러리를 사용 <html>태그가 썪여 디자인 됨.
	
	
	private int count;
	
	@ManyToOne   //board(many)to User(One) 한명의 유저는 여러개의 게시글을 쓸 수 있다.
	@JoinColumn(name = "userId")  //userId는 필드 이름
	private User user;// 데이터 베이스는 오브젝트를 저장할 수 없다. 그래서 Fk(포린키)를 사용하는데 자바는 오브젝트를 저장 할 수 있다.
	// mappedBy = "board" 데이터 베이스 필드 이름이 아닌 entity 오브잭트를 넣는 이유??? 1 대 N 일 경우 List등 자료형을 사용하여 불러오기 위함. 
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) //하나의 게시글에는 여러게의 댓글이 가능하다. mappedBy가 나오면 연관관계 주인이 아니니 컬럼을 만들지 마라라는 뜻 
	private List<Reply> reply; //join문으로 작동
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
