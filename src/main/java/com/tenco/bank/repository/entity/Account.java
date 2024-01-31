package com.tenco.bank.repository.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import org.springframework.http.HttpStatus;

import com.tenco.bank.handler.exception.CustomRestfulException;

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
	public void checkPassword(String password) {
		if(this.password.equals(password) == false) {
			throw new CustomRestfulException("계좌비밀번호틀림!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	// 잔액여부 확인 기능
	public void checkBalance(Long amount) {
		if(this.balance < amount) {
			throw new CustomRestfulException("출금잔액부족!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	// 계좌 소유자 확인 기능
	public void checkOwner(Integer principalId) {
		if(this.userId != principalId) {
			throw new CustomRestfulException("계좌소유자가아닙니다!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	// 포메터 (금액)
	public String formatBalance() {
		DecimalFormat df = new DecimalFormat("#,###");
		String formaterNumber = df.format(balance);
		return formaterNumber + "원";
	}
	
	
	
	
}
