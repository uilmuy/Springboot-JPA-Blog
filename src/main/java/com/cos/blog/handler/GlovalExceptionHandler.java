package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice // 모든 익셉션이 발생하면 이리로 들어 온다.
@RestController
public class GlovalExceptionHandler {

	@ExceptionHandler(value = Exception.class) // IllegalArgumentException 이 발생하면 아래 함수 호출// 모든 익셉견은 부모 Exception으로 처리가능
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

}
