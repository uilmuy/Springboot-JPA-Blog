package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails타입의 오브젝트를 시큐리티의 고유 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;  //콤포지션
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	//계정이 만료되지 않았는지를 리턴하는 메소드(유지:true 만료:false)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
    //계정이 잠겨있는지 아닌지 리턴하는 메소드(유지:true 만료:false)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	//비밀번호가 만료되었는지 아닌지를 리턴해줌(유지:true 만료:false)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	//계정 활성화(사용가능)가 되어있는지 아닌지를 리턴한다.
	@Override
	public boolean isEnabled() {
		return true;
	}
	//계정의 권한 상태를 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*
		 *  collectors.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole(); //스프링에서 권한을 받을때의 규칙으로 "ROLE_"+를 꼭 넣어야 한다. //ROLE_USER
			}
		});
		*/
		collectors.add(()->{return "ROLE_"+user.getRole(); 
		});
		
		return collectors;
	}
	
	
}
