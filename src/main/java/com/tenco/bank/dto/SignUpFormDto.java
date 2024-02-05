package com.tenco.bank.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpFormDto {

	// id = > AI 걸어놈 ( 자동생성 )
	private String username;
	private String password;
	private String fullname;
	private String userimg;
	private Integer point;
	private MultipartFile file;
	// --> MultipartFile file 는 name 속성 값 과 동일 해야만 한다!
	// 파일 처리
	
	
}
