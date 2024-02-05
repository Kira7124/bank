package com.tenco.bank.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
		
		
		
		System.out.println("dto :" + dto.toString());
		System.out.println(dto.getFile().getOriginalFilename());
		
		
		//  1. 인증검사 x 
		//  2. 유효성검사 o
//		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
//			throw new CustomRestfulException("username을 입력하세요!", HttpStatus.BAD_REQUEST);
//		}
//		
//		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
//			throw new CustomRestfulException("password를 입력하세요!", HttpStatus.BAD_REQUEST);
//		}
//		
//		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
//			throw new CustomRestfulException("fullname을 입력하세요!", HttpStatus.BAD_REQUEST);
//		}
		
		
		//파일업로드 
		MultipartFile file = dto.getFile();
		if(file.isEmpty() == false) {
			// 사용자가 이미지를 업로드 했다면 기능구현
			// 1. 파일사이즈 체크
			// ++ 20MB --> 1024 * 1024 * 20
			if(file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfulException("파일크기는 20MB 이상 안됩니다", HttpStatus.BAD_REQUEST);
			}
			
			
			// 2.서버 컴퓨터에 파일을 넣을 디렉토리가 있는지 검사 해주기
			String saveDirectory = Define.UPLOAD_FILE_DIRECTORY;
			
			// 폴더가 없다면 오류 발생(파일 생성시)
			File dir = new File(saveDirectory);
			if(dir.exists() == false) {
				dir.mkdir(); // 폴더가 없을때 폴더를 생성해주는 녀석
			}
			
			
			// DB 에 저장하는 파일명 / 오리지널 파일명 따로 관리해야함
			// 파일 이름(중복처리예방)
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			System.out.println("fileName :" + fileName);
			
			
			

			
			
			
		}
		
		
		//userService.createUser(dto);
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
		// Define.PRINCIPAL --> 이안에 "principal" 이 되어있어서 user 객체가 "principal" 안에 담겨있는것이랑 동일!
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
	
	
	
	
	
	//마이페이지
	@GetMapping("/detail-user")
	public String memberDetail(SignUpFormDto dto,Model model) {
		User Userdetail = userService.detailUser(dto);
		model.addAttribute("userdetail", Userdetail);
		return "user/detail";
	}
	
	
	
	
	
	//아이디중복체크
	@GetMapping("/checkID")
	@ResponseBody
	public String checkIDGet(@RequestParam("username") String username) {
		Integer result = userService.checkID(username);
		
		if(result == 1) {
			return "duplicate";
		} 
		
		else {
			return "not-duplicate";
		}
		
		
	 }
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
