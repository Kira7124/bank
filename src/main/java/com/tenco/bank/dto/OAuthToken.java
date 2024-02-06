package com.tenco.bank.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(value= PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthToken {
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private String scope;
	private Integer expiresIn;
	
}
