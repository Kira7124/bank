package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service // IoC 의 대상
public class UserService {
	
	// DB 접근 -> DI 처리
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 회원가입 로직처리
	 * @param SignUpFormDto
	 * return void;
	 */
	
	
	
	// 회원가입
	@Transactional
	public void createUser(SignUpFormDto dto) {
		
		// 추가 개념 암호화 처리
		
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(passwordEncoder.encode(dto.getPassword()))
				.fullname(dto.getFullname())
				.build();
		
		int result = userRepository.insert(user);
		
		if(result != 1) {
			throw new CustomRestfulException("회원가입실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
	@Transactional
	public void deleteUser(SignUpFormDto dto) {
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
		int result = userRepository.deleteById(user);
		
		
		if(result != 1) {
			throw new CustomRestfulException("회원삭제실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
	
	
	@Transactional
	public User detailUser(SignUpFormDto dto) {
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
		
		User userEntity = userRepository.findByUsername(user);
		
		return userEntity;
		
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * 로그인 처리
	 * @param SignInFormDto
	 * @return User
	 */
	public User readUser(SignInFormDto dto) {
		
		// 1.사용자의 username 만 받아서 정보를 추출할거임.
		User userEntity = userRepository.findByUsername2(dto.getUsername());
		
		if(userEntity == null) {
			throw new CustomRestfulException("존재하지 않는 계정입니다", HttpStatus.BAD_REQUEST);
		}
		
		
		boolean isPwdMathched = passwordEncoder.matches(dto.getPassword(), userEntity.getPassword());
		
		
		if(isPwdMathched == false) {
			throw new CustomRestfulException("비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST);
		}
		
		
		User user =  User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
		
		
		// User userEntity =  userRepository.findByUsernameAndPassword(user);

		
		return userEntity;
		
	}
	
	
	
	
	public Integer pointUser(String name) {
		
		Integer pointup = userRepository.pointChange(name);
		
		return pointup;
	}
	
	
	
	
	
}
