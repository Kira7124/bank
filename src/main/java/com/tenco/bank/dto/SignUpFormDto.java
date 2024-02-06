package com.tenco.bank.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpFormDto {

	// id = > AI 걸어놈 ( 자동생성 )
	private String username;
	private String password;
	private String fullname;
	private String userimg;
	private Integer point;
	private MultipartFile customFile;
	// --> MultipartFile file 는 name 속성 값 과 동일 해야만 한다!
	// 파일 처리
	private String originFileName;
	private String uploadFileName;
	
}
