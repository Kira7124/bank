<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>


<!-- main  -->

	<div class="col-sm-8">
		<h2>계좌생성페이지 (인증필요)</h2>
		<h5>어서오세요 <span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
		<form action="/account/save" method="post" >
			  <div class="form-group">
			    <label for="number">계좌번호</label>
			    <input type="text" name="number" class="form-control" placeholder="등록된번호입력" id="number" value="5555">
			  </div>
			  
			  <div class="form-group">
			    <label for="pwd">계좌비밀번호</label>
			    <input type="password" name="password" class="form-control" placeholder="등록된PW입력" id="pwd">
			  </div>
			  
			  <div class="form-group">
			    <label for="balance">입금금액</label>
			    <input type="text" name="balance" class="form-control" placeholder="입금금액입력" id="balance" value="2000">
			  </div>
			  
			  
           <button type="submit" class="btn btn-info">계좌생성</button>
         </form>
	  </div>
	 </br>
   </div>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->