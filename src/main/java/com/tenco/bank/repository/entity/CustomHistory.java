package com.tenco.bank.repository.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import com.tenco.bank.utils.TimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomHistory {

	private Integer id;
	private Long amount;
	private Long balance;
	private String sender;
	private String receiver;
	private Timestamp createdAt;

	
	
	
	
	// 포메터(시간)
	public String formatCreatedAt() {
		return TimeUtils.timestampToString(createdAt);
	}
	
	
	
	// 포메터 (금액)
	public String formatBalance() {
		DecimalFormat df = new DecimalFormat("#,###");
		String formaterNumber = df.format(balance);
		return formaterNumber + "원";
	}
	
	
	// 포메터 (금액)
	public String formatAmount() {
		DecimalFormat df = new DecimalFormat("#,###");
		String formaterNumber = df.format(amount);
		return formaterNumber + "원";
	}

	
	
	
	
	
	
}
