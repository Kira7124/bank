<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>


<!-- main  -->

	<div class="col-sm-8">
		<h2>출금페이지(인증필요)</h2>
		<h5>어서오세요<span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
		<form action="/account/save" method="post" >
			  <div class="form-group">
			    <label for="amount">출금금액</label>
			    <input type="text" name="amount" class="form-control" placeholder="출금액입력" id="amount" value="1000">
			  </div>
			  <div class="form-group">
			    <label for="wAccountNumber">출금계좌번호</label>
			    <input type="text" name="wAccountNumber" class="form-control" placeholder="출금계좌입력" id="wAccountNumber" value="1000">
			  </div>
			  
			  
			  <div class="form-group">
			    <label for="wAccountPassword">계좌비밀번호</label>
			    <input type="password" name="wAccountPassword" class="form-control" placeholder="등록된PW입력" id="wAccountPassword">
			  </div>
			  
           <button type="submit" class="btn btn-info">출금</button>
         </form>
	  </div>
	 <br>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->