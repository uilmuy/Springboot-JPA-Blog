package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것을 말한다.
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정 주소로 접근 하면 권한및 인증을 미리 채크 하겠다란 뜻
@EnableWebSecurity// 시큐리티 필터 추가 = 주소의 모든 request를 캐치해서 필터링 한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //Ioc컨테이너에 로딩 된다.
	public BCryptPasswordEncoder encodePWD(){
		return new BCryptPasswordEncoder();
	}
	//시큐리티가 대신 로그인을 할때 password를 가로채기 하는데 해당 패스워드가 뭘로 해쉬가 되어 회원가입 되었는지 알아야
	//같은 해쉬로 암로화해 DB랑 비교해서 로그인 할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//XSS -> 자바스크립트 공격
		//csrf -> 정상적인 서버의 주소의 데이터 전달이 아닌 것을 막는다. (ajax javascript 또한 서버간의 통신으로 보기 어렵다.)
		
		http
			.csrf().disable() //csrf 토큰 비활성화(test시 걸어두는 것이 좋다.)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
				.permitAll()
				.anyRequest() //이것이 아닌 다른 모든 요청은 인증이 되어야 접근 할 수 있다.
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") ////스프링 시큐리티가 해당 주소로 요청하는 로그인을 가로챈다.
				.defaultSuccessUrl("/")
				; 
	}

}
