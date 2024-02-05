<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>



<!-- main  -->
<script>
	function checkId(){
		const username = document.getElementById("join-id").value;
		
		   if (username.trim() === "") {
	            alert('사용자이름을 입력하세요!');
	            return;
	        }
		
		$.ajax({
			url:"/user/checkID",
			type: "GET",
			data:{
				username : username
			},
			success: function(data){
				
				if(data === "duplicate"){
					alert('이미존재하는이름입니다!');
				}
				
				if(data === "not-duplicate"){
					alert('사용가능한 이름입니다!');
				}
				
				
				
			},
			error: function(data){
				alert('에러가발생했습니다!');
			}
			
			
		});
		
	}
</script>

	<div class="col-sm-8">
		<h2>회원정보수정</h2>
		<form action="/user/updateUser" method="post" enctype="multipart/form-data">
			 <div class="form-group">
			    <input type="hidden" name="id" class="form-control" placeholder="id입력" id="id" value="${principal.id}" readonly>
			  </div>
			    <label for="username">유저네임</label>
			  <div class="form-group d-flex align-items-center">
			    <input type="text" name="username" class="form-control" placeholder="바꿀유저명입력" id="join-id">
			    <button type="button" class="btn btn-danger ml-2" id="check-id" onclick="checkId()">중복확인</button>
			  </div>
			  <div class="form-group">
			    <label for="pwd">비밀번호</label>
			    <input type="password" name="password" class="form-control" placeholder="바꿀PW입력" id="pwd">
			    <div id="pw-msg"></div>
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
   
<script>
    var pwd = document.querySelector("#pwd");
    var pwMsg = document.querySelector("#pw-msg");

    pwd.addEventListener("input", checkPw);

    function checkPw() {
        var pattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;

        if (pwd.value === "") {
            pwMsg.innerHTML = "";
        } else if (!pattern.test(pwd.value)) {
            pwMsg.innerHTML = "위험 - 8자이상입력하세요!";
            pwMsg.style.color = "#d9534f";
        } else {
            pwMsg.innerHTML = "안전한 비밀번호입니다!";
            pwMsg.style.color = "#03c75a";
        }
    }
</script>  
   
<!-- main  -->


<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->