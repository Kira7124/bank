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
	public int deleteById(Integer id);
	
	//회원조회 --> User 타입쓰는이유? -> 단일행으로 쿼리가 떨어지기때문!
	public User findById(Integer id);
	
	//회원리스트조회
	public List<User> findAll();
	
	
	
	
	
}
