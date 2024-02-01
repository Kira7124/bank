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
	  
		<div class ="col-sm-16">
			<table  class="table table-striped">
			  <thead>
				<tr>
					<th><span style="color: blue; font-weight: bold;">${name}</span> 님의 계좌</th>
					<th>계좌번호 : <span style="color: blue; font-weight: bold;">${account.number}</span></th>
					<th>잔액 : <span style="color: blue; font-weight: bold;">${account.formatBalance()}</span></th>
				</tr>
			  </thead>	
				<tr>
				 <tbody>
					<td>
						<a href="/account/detail/${account.id}">
							전체조회
						</a>
					</td>
					<td>
						<a href="/account/detail/${account.id}?type=deposit">
							입금조회
						</a>
					</td>
					<td>
						<a href="/account/detail/${account.id}?type=withdraw">
							출금조회
						</a>
					</td>
				</tr>
			    </tbody>
			</table><br>
			
			<table class= "table table-striped">
			 <thead>
				<tr>
					<th>생성일</th>
					<th>받은이</th>
					<th>보낸이</th>
					<th>금액</th>
					<th>잔액</th>
				</tr>
			  </thead>
		<c:choose>
	  		<c:when test="${historyList != null}">	  
			  <tbody>
			  	<c:forEach items="${historyList}" var="history">
				  <tr>
					<td>${history.formatCreatedAt()}</td>
					<td>${history.receiver}</td>
					<td>${history.sender}</td>
					<td>${history.formatAmount()}</td>
					<td>${history.formatBalance()}</td>
				 </tr>
				</c:forEach> 
			  </tbody>		
			</table>
		  </c:when>	
		<c:otherwise>
		  	<p>고객님의 내역이 없습니다</p>
		 </c:otherwise>
	 </c:choose>	
		</div>
	  </div>
	 </br>
   </div>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->