package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	
	
	// 회원 가입
	// 화면반환용도
	// http://localhost:80/user/sign-up
	
	/**
	 * 회원가입페이지요청
	 * @return signUp.jsp 파일 리턴
	 */
	
	
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}
	
	
	//회원가입
	//요청처리 용도
	//주소설계
	//http://localhost:80/user/sign-up
	
	
	@PostMapping("/sign-up")
	public String signProc(SignUpFormDto dto) {
		
		//  1. 인증검사 x 
		//  2. 유효성검사 o
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		
		userService.createUser(dto);
		
		// todo 로그인 페이지로 변경 예정 ! (아직은 가칭으로 달아놓음)
		return "redirect:/user/sign-up";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
