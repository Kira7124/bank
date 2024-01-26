package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;



@Getter
public class CustomPageException extends RuntimeException {
	
	// 상태코드
	private HttpStatus httpStatus;
	
	public CustomPageException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	
	//사용하는시점에 활용가능
	//new CustomPageException("바보야", HttpStatus.ok);
	
	
	
}
