package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;





@Controller // 데이터 를 (만) 반환하고싶으면 -> @RestController 달아주기
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	// ++생성자 의존주입 -> DI (직접 만들어도 됨)
	
	@Autowired
	private AccountService accountService;
	// ++생성자 의존주입 -> DI (직접 만들어도 됨)
	
	
	
	
	// 주소설계
	// http://localhost:80/account/save

	
	
	
	// 페이지 요청
	@GetMapping("/save")
	public String savePageGet() {
		// 인증검사
		
		/**
		 * 계좌생성페이지요청
		 * @return saveForm.jsp
		 */
		
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		
		
		return "account/saveForm";
	}
	
	
	
	@PostMapping("/save")
	public String savePagePost(AccountSaveFormDto dto) {
		
		
		
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getBalance() < 0) {
			throw new CustomRestfulException("잘못된 금액입니다!", HttpStatus.BAD_REQUEST);
		}
		
		
		//DTO 작성 
		
		//Repository 작성 후 account.xml mapper id 참고하기
		
		//Service 작성
		
		//Service 호출후 처리 (계좌생성처리)
		
		// ++ 유효성 검사 처리달아주기!!(if 문으로 작성)
		// ++ 인증검사 
		// ++ 서비스 호출
		// ++ 응답처리 
		// +++ 파싱? -> (DTO 방식으로)
		// todo -> 수정예정...
		
		accountService.createAccount(dto, principal.getId());
		return "redirect:/user/sign-in";
		
	}
	
	
	
	
	
}
