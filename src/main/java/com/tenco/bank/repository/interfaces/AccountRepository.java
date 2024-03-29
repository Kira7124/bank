package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.CustomHistory;

//interface + xml 연결 ( mapper framework 규칙 )
@Mapper
public interface AccountRepository {

	
	//계좌등록
	public int insert(Account account);
	
	//계좌수정
	public int updateById(Account account);
	
	//계좌삭제
	public int deleteById(Integer id);
	
	// 계좌조회 - 1 유저 , N 계좌 (여러개)
	public List<Account> findAllByUserId(Integer userId);
	
	// 하나의 계좌 조회
	public Account findByNumber(String number);
	
	// 하나의 계좌 조회2 --> id 로 조회
	public Account findAllById(Integer id);
	
	//중복계좌조화
	public Integer checkAccountNum(String number);
	
	
	
	
}
