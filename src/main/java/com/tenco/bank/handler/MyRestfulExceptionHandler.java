package com.tenco.bank.handler;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Order(1)
@RestControllerAdvice
public class MyRestfulExceptionHandler {

	// 모든 예외 클래스 설정
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		log.debug("-------------------------------------");
		//System.out.println("---------------------------");
		log.debug(e.getClass().getName());
		log.debug(e.getMessage());
		//System.out.println(e.getClass().getName());
		//System.out.println(e.getMessage());
		//System.out.println("---------------------------");
		log.debug("-------------------------------------");


		
	}
	
	
	
	
	
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		StringBuffer sb = new StringBuffer(); 
		sb.append("<script>");
		sb.append("alert('"+ e.getMessage() +"');");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}
	
	
	
	
	@ExceptionHandler(UnAuthorizedException.class)
	public String unAuthorizedException(UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer(); 
		sb.append("<script>");
		sb.append("alert('"+ e.getMessage() +"');");
		sb.append("location.href='/user/sign-in';");
		sb.append("</script>");
		return sb.toString();
	}
	
	
	
	
	
}
