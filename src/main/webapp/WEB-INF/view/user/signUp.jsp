<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<!-- main  -->



<script>
	function checkId(){
		const username = document.getElementById("join-id").value;
		
		   if (username.trim() === "") {
			   Swal.fire({
			        title: '빈칸입니다!',
			        icon: 'error',
			        text: '빈칸입니다! 입력해주세요.',
			    });	         
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
					 Swal.fire({
					        title: '아이디존재!',
					        icon: 'error',
					        text: '중복된아이디입니다! 다른 아이디를 입력해주세요.',
					    });	     
				}
				
				if(data === "not-duplicate"){
					 Swal.fire({
					        title: '사용사능한아이디!',
					        icon: 'success',
					        text: '사용가능한아이디입니다!',
					   });	     
				}
				
				
				
			},
			error: function(data){
				alert('에러가발생했습니다!');
			}
			
			
		});
		
	}
</script>



		<div class="col-sm-8">
			<h2>회원가입</h2>
			<h5>어서오세요 환영합니다!</h5>
			<form action="/user/sign-up" method="post" enctype="multipart/form-data">
				    <label for="username">사용자명</label>
				  <div class="form-group d-flex align-items-center">
				    <input type="text" name="username" class="form-control" placeholder="이름을입력하세요" id="join-id">
				    <button type="button" class="btn btn-danger ml-2" id="check-id" onclick="checkId()">중복확인</button>
				  </div>
				  <div class="form-group">
				    <label for="pwd">비밀번호</label>
				    <input type="password" name="password" class="form-control" placeholder="비밀번호를입력하세요" id="pwd">
				    <div id="pw-msg"></div>
				  </div>
				  <div class="form-group">
				    <label for="fullname">풀네임</label>
				    <input type="text" name="fullname" class="form-control" placeholder="풀네임을입력하세요" id="fullname">
				  </div>
			  		<!-- 이벤트 전파속성 -> 버블링? 캡처링? 뭔가용? -->	  
			   	   <div class="custom-file">
					  <input type="file" class="custom-file-input" id="customFile" name="customFile"><br>
					  <label class="custom-file-label" for="customFile">사진변경</label>
				   </div>
			    <div class="form-group">
		            <form action="/auth/send" method="get">
		                <label for="email">이메일 주소</label>
		                <input type="email" class="form-control" id="email" name="email" placeholder="이메일을 입력하세요" required>
		                <button type="submit" class="btn btn-primary mt-2">이메일로 인증 메일 받기</button>
		            </form>
        		</div>
	           <button type="submit" class="btn btn-info">회원가입</button>
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