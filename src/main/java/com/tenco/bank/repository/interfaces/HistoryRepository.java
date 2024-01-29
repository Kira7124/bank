package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.entity.History;
import com.tenco.bank.repository.entity.User;

//interface + xml 연결 ( mapper framework 규칙 )
@Mapper
public interface HistoryRepository {

	//등록
	public int insert(History history);
	
	//수정
	public int updateById(History history);
	
	//삭제
	public int deleteById(Integer id);
	
	//계좌조회
	public History findById(User id);
	
	//계좌 다중갯수 조회
	public List<History> findAll();
	
	
	

}
