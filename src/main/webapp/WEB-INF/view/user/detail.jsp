<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>


<!-- main  -->

	<div class="col-sm-8">
		<h2>마이페이지</h2>
		<h5>어서오세요 <span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
		<p><img src="${principal.setupUserImage()}"></p>
		<p>이름 : ${userdetail.username}</p>
		<p>풀네임 : ${userdetail.fullname}</p>
		<p>포인트 : ${userdetail.point}</p>
	    <c:choose>
	      <c:when test="${principal.point >= 100}">
	    	등급 : <span style="color:gold; font-weight: bold;">골드</span>
	      </c:when>
	      <c:when test="${principal.point >= 50}">
	    	등급 : <span style="color:#dcdcdc; font-weight: bold;">실버</span>
	      </c:when>
	 	  <c:otherwise>
	 		 등급 : <span style="color:#a52a2a; font-weight: bold;">브론즈</span>
	 	  </c:otherwise> 
	    </c:choose>
	    
	  </div>
	</br>
   </div>
   </div>
   
   

  
  
   
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->