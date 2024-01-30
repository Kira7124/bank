package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	
	/**
	 * 회원가입 로직처리
	 * @param SignUpFormDto
	 * return void;
	 */
	
	
	
	// 회원가입
	@Transactional
	public void createUser(SignUpFormDto dto) {
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
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
		
		User user =  User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
		
		
		User userEntity =  userRepository.findByUsernameAndPassword(user);
		
		if(userEntity == null) {
			throw new UnAuthorizedException("인증된 사용자가 아닙니다",HttpStatus.UNAUTHORIZED);
		}
		
		return userEntity;
		
	}
	
	
	
	
	
	
	
	
	
}
