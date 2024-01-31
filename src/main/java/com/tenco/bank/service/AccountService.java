package com.tenco.bank.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.TransferFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.History;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.interfaces.HistoryRepository;
import com.tenco.bank.utils.Define;

@Service // IoC 의 대상 + Singleton 으로 관리되어짐 !
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
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


	// 출금기능 만들기
	// 1 . 계좌존재 여부확인 -- Select 쿼리로 확인 
	// 2 . 본인계좌여부 확인 -- Select 쿼리로 확인
	// 3 . 사용자 계좌비번 확인
	// 4 . 잔액여부 확인 
	// 5 . 출금처리 --> Update 쿼리 
	// 6 . 거래내역등록 --> Insert 쿼리
	// 7 . 트랜잭션 처리 
	
	
	
	@Transactional
	public void updateAccountWithdraw(WithdrawFormDto dto, Integer principalId) {
		
		// 1
		Account accountEntity = accountRepository.findByNumber(dto.getWAccountNumber());

		if(accountEntity == null ) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR);
			
			
		}
		
		
		// 2 
		accountEntity.checkOwner(principalId);
		
		
		
		//3 
		accountEntity.checkPassword(dto.getWAccountPassword());
		
		
		//4 
		accountEntity.checkBalance(dto.getAmount());
		
		//5 
		// 현재 객체의 상태값을 변경
		accountEntity.withdraw(dto.getAmount());
		accountRepository.updateById(accountEntity);
		
		
		
		//6
		//거래내역등록
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null);
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		
		
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}



	
	@Transactional
	public void updateAccountDeposit(DepositFormDto dto,Integer principalId) {
		
		Account accountEntity = accountRepository.findByNumber(dto.getDAccountNumber());
		
		if(accountEntity == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		// 2 
		accountEntity.checkOwner(principalId);
		
		//3 
		accountEntity.checkPassword(dto.getDAccountPassword());
		
		
		// 업데이트
		accountEntity.deposit(dto.getAmount());
		accountRepository.updateById(accountEntity);

		
		//거래내역등록
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(null);
		history.setDBalance(accountEntity.getBalance());
		history.setWAccountId(null);
		history.setDAccountId(accountEntity.getId());
		
		
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

	
	
	// 이체기능만들기
	// 1. 출금계좌존재여부 확인 - Select
	// 2. 입금계좌존재여부 확인 - Select
	// 3. 출금계좌본인소유 확인  
	// 4. 출금계좌비밀번호 확인 - Object
	// 5. 출금계좌잔액 확인  - Object
	// 6. 출금계좌잔액 수정  - Update
	// 7. 입금계좌잔액 수정  - Update
	// 8. 거래내역등록 처리 (송금내역 쿼리 테스트 선행)
	// 9. 트랜잭션처리
	
	
	@Transactional
	public void transferAccount(DepositFormDto ddto, WithdrawFormDto wdto,Integer principalId) {
		// 출금계좌존재여부확인 , 입금계좌존재여부확인
		Account accountentity1 = accountRepository.findByNumber(wdto.getWAccountNumber());
		Account accountentity2 = accountRepository.findByNumber(ddto.getDAccountNumber());
		
		
		if(accountentity1 == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		if(accountentity2 == null) {
			throw new CustomRestfulException(Define.NOT_EXIST_ACCOUNT, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		//출금계좌본인소유확인
		accountentity1.checkOwner(principalId);
		
		//출금계좌비밀번호확인
		accountentity1.checkPassword(wdto.getWAccountPassword());
		
		
		
		//상태업데이트
		accountentity1.withdraw(wdto.getAmount());
		accountentity2.deposit(ddto.getAmount());
		accountentity1.checkBalance(wdto.getAmount());
		accountentity2.checkBalance(ddto.getAmount());
		accountRepository.updateById(accountentity1);
		accountRepository.updateById(accountentity2);
	
		
		//거래내역등록처리(쿼리테스트선행)
		//거래내역등록
		History history = new History();
		history.setAmount(ddto.getAmount()-wdto.getAmount());
		history.setWBalance(accountentity1.getBalance());
		history.setDBalance(accountentity2.getBalance());
		history.setWAccountId(accountentity1.getId());
		history.setDAccountId(accountentity2.getId());
		
		
		int rowResultCount = historyRepository.insert(history);
		if(rowResultCount != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	
	

	
	
	
	
}
