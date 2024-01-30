package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.User;

import jakarta.servlet.http.HttpSession;





@Controller // 데이터 를 (만) 반환하고싶으면 -> @RestController 달아주기
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	// ++생성자 의존주입 -> DI
	
	
	// 주소설계
	// http://localhost:80/account/save

	
	
	
	// 페이지 요청
	@GetMapping("/save")
	public String savePage() {
		// 인증검사
		
		/**
		 * 계좌생성페이지요청
		 * @return saveForm.jsp
		 */
		
		User principal = (User)session.getAttribute("principal");
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		
		
		return "account/saveForm";
	}
	
	
	
	
	
}
