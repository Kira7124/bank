<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>



<!-- main  -->

	<div class="col-sm-8">
		<h2>회원정보수정</h2>
		<form action="/user/updateUser" method="post" enctype="multipart/form-data">
			 <div class="form-group">
			    <label for="username">id</label>
			    <input type="text" name="id" class="form-control" placeholder="id입력" id="id" value="${principal.id}" readonly>
			  </div>
			  <div class="form-group">
			    <label for="username">유저네임</label>
			    <input type="text" name="username" class="form-control" placeholder="바꿀유저명입력" id="username">
			  </div>
			  <div class="form-group">
			    <label for="pwd">비밀번호</label>
			    <input type="password" name="password" class="form-control" placeholder="바꿀PW입력" id="pwd">
			  </div>
			   <div class="form-group">
			    <label for="fullname">풀네임</label>
			    <input type="text" name="fullname" class="form-control" placeholder="바꿀풀네임입력" id="fullname">
			  </div>
			  <div class="custom-file">
				<input type="file" class="custom-file-input" id="customFile" name="customFile"><br>
				<label class="custom-file-label" for="customFile">파일선택</label>
			  </div>
           <button type="submit" class="btn btn-info">회원정보수정</button>
         </form>
	  </div>
	</br>
   </div>
   </div>
   
  
  
  
  
 
 <script>
	$(".custom-file-input").on("change", function() {
	  var fileName = $(this).val().split("\\").pop();
	  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
	});
</script>     
   
   
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->