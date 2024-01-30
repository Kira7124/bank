package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.interfaces.AccountRepository;

@Service // IoC 의 대상 + Singleton 으로 관리되어짐 !
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	
	
	
	// 계좌생성
	// 사용자 정보 필요
	// todo -> 계좌번호 중복 확인 예정....
	@Transactional
	public void createAccount(AccountSaveFormDto dto,Integer principalId) {
			
		Account account = new Account();
		account.setNumber(dto.getNumber());
		account.setPassword(dto.getPassword());
		account.setBalance(dto.getBalance());
		account.setUserId(principalId);
		
		
		int resultRowcount = accountRepository.insert(account);
		if(resultRowcount != 1) {
			throw new CustomRestfulException("계좌생성실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	
	
	
	
}
