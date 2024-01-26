package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.handler.exception.CustomRestfulException;



@Controller //view 만 보여줌
@RequestMapping("/test1") // 대문
public class TestController {

	
	// 주소설계	
	// http://localhost:80/test1/main
	
 @GetMapping("/main")
 public void mainpageGET() {
	 System.out.println("111111111111111111");
	 // 인증검사 -> 정상적인 , 잘못된사용자인지 검사
	 // 유효성검사 추 후 진행
	 // 뷰 리졸브 --> 해당하는 파일 찾아 (data)  
	 // return "/WEB-INF/view/layout/main.jsp";
	 //     prefix: /WEB-INF/view/
	 //				/layout/main
     //		suffix: .jsp
	// /WEB-INF/view//layout/main.jsp -? x
	 
	 
	 
	 
	// 예외 발생 
	throw new CustomRestfulException("페이지가 없네요", HttpStatus.NOT_FOUND);
	// return "layout/main";
 }
	




}
