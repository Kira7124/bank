<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>


<!-- main  -->

	<div class="col-sm-8">
		<h2>나의 계좌목록</h2>
		<h5>어서오세요 <span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
	  <!-- detail 시작 -->
	  
		<div class ="col-sm-10">
			<table  class="table table-striped">
			  <thead>
				<tr>
					<th><span style="color: red; font-weight: bold;">${name}</span> 님의 계좌</th>
					<th>계좌번호 : ${accountHead.number}</th>
					<th>잔액 : ${accountHead.balance}원</th>
				</tr>
			  </thead>	
				<tr>
				 <tbody>
					<td>
						<a href="#">
							전체조회
						</a>
					</td>
					<td>
						<a href="#">
							입금조회
						</a>
					</td>
					<td>
						<a href="#">
							출금조회
						</a>
					</td>
				</tr>
			    </tbody>
			</table><br>
			
			<table class= "table table-striped">
			 <thead>
				<tr>
					<th>날짜</th>
					<th>보낸이</th>
					<th>받은이</th>
					<th>입출금금액</th>
					<th>계좌잔액</th>
				</tr>
			  </thead>
			  <tbody>
				<tr>
					<td>1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
					<td>5</td>
				</tr>
			  </tbody>		
			</table>
	
		</div>
	
		
		
		
	  </div>
	 </br>
   </div>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->