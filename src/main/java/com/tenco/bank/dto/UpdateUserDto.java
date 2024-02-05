package com.tenco.bank.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateUserDto {
	
	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private MultipartFile customFile;
	private String originFileName;
	private String uploadFileName;
	
	
}
