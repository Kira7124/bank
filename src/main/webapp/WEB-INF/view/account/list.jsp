<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>


<!-- main  -->

	<div class="col-sm-8">
		<h2>나의 계좌목록</h2>
		<h5>어서오세요 <span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
	  <!-- 만약 accountList null or not null -->
<c:choose>
	  <c:when test="${accountList != null}">
	  	<table class="table table-bordered table-sm">
			<thead>
				<tr>
					<th>계좌번호</th>
					<th>잔액</th>
				</tr>	
			</thead>
			<tbody>
			  <c:forEach items="${accountList}" var="account">
				<tr>
					<td>${account.number}</td>
					<td>${account.balance}</td>
				</tr>
			  </c:forEach>
			</tbody>
		</table>
	  </c:when>
	 <c:otherwise>
	  	<p>고객님의 계좌가 없습니다</p>
	  	<a href ="/account/save">계좌생성하기</a>
	 </c:otherwise>
</c:choose>	
	
		
		
		
	  </div>
	 <br>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->