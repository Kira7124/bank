<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>


<!-- main  -->

	<div class="col-sm-8">
		<h2>로그인</h2>
		<h5>어서오세요 <span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
		<form action="/user/sign-in" method="post" >
			  <div class="form-group">
			    <label for="username">유저네임</label>
			    <input type="text" name="username" class="form-control" placeholder="등록된ID입력" id="username">
			  </div>
			  <div class="form-group">
			    <label for="pwd">비밀번호</label>
			    <input type="password" name="password" class="form-control" placeholder="등록된PW입력" id="pwd">
			  </div>
           <button type="submit" class="btn btn-info">로그인</button>
         </form>
	  </div>
	 <br>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->