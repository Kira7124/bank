package com.tenco.bank.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bank.dto.KakaoProfile;
import com.tenco.bank.dto.OAuthToken;
import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.dto.UpdateUserDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		System.out.println(dto.getCustomFile().getOriginalFilename());
		
		
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
		
		
		//파일업로드 
		MultipartFile file = dto.getCustomFile();
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
			
			
			String uploadPath = Define.UPLOAD_FILE_DIRECTORY + File.separator + fileName;
			File destination = new File(uploadPath);
			
			
			
			try {
				file.transferTo(destination);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			// 객체상태변경
			dto.setOriginFileName(file.getOriginalFilename());
			dto.setUploadFileName(fileName);
			
			
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
		// Define.PRINCIPAL --> 이안에 "principal" 이 되어있어서 user 객체가 "principal" 안에 담겨있는것이랑 동일!
		httpSession.setAttribute("name", user.getUsername());
		//로그인 완료 --> 페이지 결정 (account/list)
		// todo 수정예정 (현재 접근 경로없음)
		return "redirect:/account/list";
	}
	
	
	//로그아웃
	@GetMapping("/sign-out")
	public String userlogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/user/sign-in";
	}
	
	
	
	//회원삭제 GET
	@GetMapping("/delete-user")
	public String userDeleteget() {
		return "user/delete";
	}
	
	
	//회원삭제 POST
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
	
	
	
	
	
	//마이페이지 GET
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
	


	 // http://localhost:80/user/updateUser
	 // 회원정보수정 GET
	 @GetMapping("/updateUser")
	 public String updateUserGET() {
		 return "user/update";
	 }
	
	 
	 
	 
	 
	 // 회원정보수정 POST
	 @PostMapping("/updateUser")
	 public String updateUserPOST(UpdateUserDto dto) {
		 
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
			
			
			//파일업로드 
			MultipartFile file = dto.getCustomFile();
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
				
				
				String uploadPath = Define.UPLOAD_FILE_DIRECTORY + File.separator + fileName;
				File destination = new File(uploadPath);
				
				
				
				try {
					file.transferTo(destination);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				// 객체상태변경
				dto.setOriginFileName(file.getOriginalFilename());
				dto.setUploadFileName(fileName);
				
			}
			
		 userService.updateById(dto);
		 return "redirect:/user/sign-in";
	 }
	
	
	
	 // http://localhost:80/user/kakao-callback?code="xxxxxxxxxx"
	 @GetMapping("/kakao-callback")
	 public String kakaoCallback(@RequestParam String code) {
		 System.out.println("code : " + code); 
		 
		 
		//POST 방식 , 헤더구성 , 바디구성
		RestTemplate rt1 = new RestTemplate();
		 
		 //헤더구성
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//바디구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "889b8927906da3de1056fe5e6264e321");
		params.add("redirect_uri", "http://localhost/user/kakao-callback");
		params.add("code", code);
		
		
		//헤더 + 바디 결합
		HttpEntity<MultiValueMap<String, String>> reqMsg = new HttpEntity<>(params,headers1);
		ResponseEntity<OAuthToken> response = 
				rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, reqMsg, OAuthToken.class);
	
		
		//DTO 설계하기 --> 작성완 --> 간단작성해주는 웹 사이트 활용
		
	
		//다시요청 -- 인증토큰 -- 사용자정보요청
		RestTemplate rt2 = new RestTemplate();
		
		//헤더2
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + response.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//바디2 --> 여기서는 필수값이 아님 ( 넣어도 되긴 함! )
		
		
		//결합
		HttpEntity<MultiValueMap<String, String>> kakaoInfo = new HttpEntity<>(headers2);
		ResponseEntity<KakaoProfile> response2 = 
				rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoInfo, KakaoProfile.class);
		System.out.println(response2.getBody());

		
		KakaoProfile kakaoProfile = response2.getBody();
		
		
		// 로그인처리
		// 단 , 최초 요청 사용자라면 , 회원가입 후 로그인처리
		// 우리사이트 --> 카카오
		SignUpFormDto dto = SignUpFormDto.builder()
				.username("OAuth_" + kakaoProfile.getProperties().getNickname())
				.fullname("Kakao")
				.password("asd1234")
				.build();
		
		User oldUser = userService.readUserByUserName(dto.getUsername());
		// null <--- 최초로그인시에
		if(oldUser == null) {
			userService.createUser(dto);
			oldUser = new User();
			oldUser.setUsername(dto.getUsername());
			oldUser.setFullname(dto.getFullname());
		}
		
		oldUser.setPassword(null);
		// 로그인처리
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		
		
		return "redirect:/account/list";
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
