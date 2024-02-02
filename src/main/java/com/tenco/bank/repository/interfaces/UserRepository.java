package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.entity.User;

//interface + xml 연결 ( mapper framework 규칙 )
@Mapper
public interface UserRepository {
	
	
	// 회원가입
	public int insert(User user);
	
	// 회원수정
	public int updateById(User user);
	
	// 회원삭제
	public int deleteById(User user);
	
	//회원조회 --> User 타입쓰는이유? -> 단일행으로 쿼리가 떨어지기때문!
	public User findById(Integer id); // 관리자에서 활용가능!
	
	//회원리스트조회
	public List<User> findAll();
	
	//사용자 username으로 존재여부확인 -> 중복검사 등 에 활용
	public User findByUsername(User user);
	
	
	//사용자 username으로 존재여부확인 -> 중복검사 등 에 활용2
	public User findByUsername2(String username);
	
	
	// 아이디 , 비밀번호로 회원조회 -> 로그인처리
	public User findByUsernameAndPassword(User user);
	
	// 포인트증가 -> 유저네임받아서
	public Integer pointChange(String username);
	
	//아이디중복체크 ->유저네임받아서
	public Integer checkID(String username);
	
}
