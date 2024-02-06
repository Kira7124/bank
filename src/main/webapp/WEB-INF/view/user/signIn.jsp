<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>


<!-- main  -->

	<div class="col-sm-8">
		<h2>로그인</h2>
		<form action="/user/sign-in" method="post" >
			  <div class="form-group">
			    <label for="username">유저네임</label>
			    <input type="text" name="username" class="form-control" placeholder="등록된ID입력" id="username" value="${rememberedId != null ? rememberedId : ''}"/>
				<input type="checkbox" id="checkid" name="remember" value="chk" ${rememberedId != null ? 'checked' : ''}/><span>아이디기억하기</span>
			  </div>
			  <div class="form-group">
			    <label for="pwd">비밀번호</label>
			    <input type="password" name="password" class="form-control" placeholder="등록된PW입력" id="pwd" value="12345678">
			  </div>
           <button type="submit" class="btn btn-info">로그인</button>
           <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=889b8927906da3de1056fe5e6264e321&redirect_uri=http://localhost/user/kakao-callback">
           		<img alt="" src="/images/kakao_login_small.png" width="75" height="38">
           </a>
         </form>
	  </div>
	</br>
   </div>
   </div>
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->