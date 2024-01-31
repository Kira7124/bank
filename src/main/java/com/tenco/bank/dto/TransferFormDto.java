package com.tenco.bank.dto;

import lombok.Data;

@Data
public class TransferFormDto {

	private Long amount;
	private String tAccountNumber;
	private String tAccountPassword;
	
	
}
