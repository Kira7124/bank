package com.tenco.bank.dto;

import lombok.Data;

@Data
public class WithdrawFormDto {

	private Long amount;
	private String wAccountNumber;
	private String wAccountPassword;
	

}
