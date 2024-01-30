package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.utils.Define;

@Service // IoC 의 대상 + Singleton 으로 관리되어짐 !
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	
	
	
	// 계좌생성
	// 사용자 정보 필요
	// todo -> 계좌번호 중복 확인 예정....
	
	
	
	
	// 단일계좌 검색기능 --> 중복확인용 
	public Account readAccount(String number) {
		
		Account accountEntity = accountRepository.findByNumber(number.trim());
				
		return accountEntity;
	}
	
	// 계좌목록보기
	public List<Account>readAccountListByUserId(Integer principalId){
		return accountRepository.findAllByUserId(principalId);
	}
	
	
	@Transactional
	public void createAccount(AccountSaveFormDto dto,Integer principalId) {
		
		
		
		// 계좌번호 중복확인 , 예외처리
		if(readAccount(dto.getNumber()) != null ) {
			throw new UnAuthorizedException(Define.EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		Account account = new Account();
		account.setNumber(dto.getNumber());
		account.setPassword(dto.getPassword());
		account.setBalance(dto.getBalance());
		account.setUserId(principalId);
		
		
		int resultRowcount = accountRepository.insert(account);
		if(resultRowcount != 1) {
			throw new CustomRestfulException(Define.FAIL_TO_CREATE_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	

	
	
	
	
}
