package com.tenco.bank.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
	
	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;
	
	
	
	// 출금 기능 메서드(선언,바디)
	
	public void withdraw(Long amount) {
		// 방어적 코드작성 -> 금액이 음수가 되거나 / ~보다 작아지면 안됨등을 세팅.
		this.balance = this.balance - amount;
		
	}
	
	
	// 입금 기능 메서드(선언,바디)
	
	public void deposit(Long amount) {
		this.balance = this.balance + amount;
		
	}
	
	
	// 패스워드 체크 기능
	// 잔액여부 확인 기능
	// 계좌 소유자 확인 기능
	
}
