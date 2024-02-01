package com.tenco.bank.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired // DI 처리
	private UserService userService;
	
	@Autowired // DI 처리
	private HttpSession httpSession;
	

	@Autowired
	private ServletContext servletContext;
	
	
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
	
	
	/**
	 * 회원가입요청
	 * @param dto
	 * @return 로그인 페이지(/sign-in)
	 */
	
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
		return "redirect:/user/sign-in";
	}
	
	
	
	
	// 1. 로그인 페이지 요청처리
	// 2. 주소설계
	// http://localhost:80/user/sign-in
	
	
	/**
	 * 로그인페이지 요청
	 * @return 
	 */
	
	
	
	@GetMapping("/sign-in")
	public String signInpage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "user/signIn";
		
	}
	
	
	
	/**
	 * 로그인요청 처리
	 * @param SignInFormDto
	 * @return 추후 account/list 페이지로 이동예정
	 */
	
	
	
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		System.out.println("111111111111111111111");
		// 1. 인증검사 -> 유효성검사
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		
		
		// 서비스 호출 예정....
		User user =	userService.readUser(dto);
		userService.pointUser(dto.getUsername());
		System.out.println("22222222222222222222222222");
		httpSession.setAttribute(Define.PRINCIPAL, user);
		httpSession.setAttribute("name", user.getUsername());
		//로그인 완료 --> 페이지 결정 (account/list)
		// todo 수정예정 (현재 접근 경로없음)
		return "redirect:/account/list";
	}
	
	
	
	@GetMapping("/sign-out")
	public String userlogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/user/sign-in";
	}
	
	
	
	
	@GetMapping("/delete-user")
	public String userDeleteget() {
		return "user/delete";
	}
	
	
	
	@PostMapping("/delete-user")
	public String userDeletepost(SignUpFormDto dto,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String Username = (String)session.getAttribute("name");
		
		
		
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		
		 if (dto.getUsername().equals(Username)) {
		        userService.deleteUser(dto);
		        session.invalidate();
		 } 
		
		 
		 if(!dto.getUsername().equals(Username)) {
			 throw new CustomRestfulException("본인 username을 입력하세요!", HttpStatus.BAD_REQUEST);
		 }
		 
		 
		return "redirect:/user/sign-in";
	}
	
	
	
	
	
	
	@GetMapping("/detail-user")
	public String memberDetail(SignUpFormDto dto,Model model) {
		User Userdetail = userService.detailUser(dto);
		model.addAttribute("userdetail", Userdetail);
		return "user/detail";
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
