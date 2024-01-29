package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service // IoC 의 대상
public class UserService {
	
	// DB 접근 -> DI 처리
	@Autowired
	private UserRepository userRepository;
	
	// 회원가입
	@Transactional
	public void createUser(SignUpFormDto dto) {
		
		
		int result = userRepository.insert(dto);
		
		if(result != 1) {
			throw new CustomRestfulException("회원가입실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
		
	}
	
	
	
}
