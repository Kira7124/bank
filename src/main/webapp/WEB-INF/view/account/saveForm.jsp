<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- header  -->

<%@ include file="/WEB-INF/view/layout/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>


<!-- main  -->

 <script>
	function checkNum(){
		const number = document.getElementById("number-id").value;
		
		
		if(number.trim() === ""){
			Swal.fire({
		        title: '빈칸입니다!',
		        icon: 'error',
		        text: '빈칸입니다! 입력해주세요.',
		    });	         
			return;
			
		}
		
		$.ajax({
			url:"/account/checkAccountNum",
			type: "GET",
			data:{
				number : number
			},
			success: function(data){
				
				if(data === "duplicate"){
					Swal.fire({
				        title: '계좌존재!',
				        icon: 'error',
				        text: '중복된계좌입니다! 다른 아이디를 입력해주세요.',
				    });	     				}
				
				if(data === "not-duplicate"){
					 Swal.fire({
					        title: '사용사능한계좌!',
					        icon: 'success',
					        text: '사용가능한계좌입니다!',
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
		<h2>계좌생성페이지 (인증필요)</h2>
		<h5>어서오세요 <span style="color: red; font-weight: bold;">${name}</span> 님 환영합니다!</h5><br>
		<form action="/account/save" method="post" >
			  <label for="number">계좌번호</label>
			 <div class="form-group d-flex align-items-center">
			    <input type="text" name="number" class="form-control" placeholder="등록된번호입력" id="number-id" value="5555">
			    <button type="button" class="btn btn-danger ml-2" id="check-id" onclick="checkNum()">중복확인</button>
			  </div>
			  
			  <div class="form-group">
			    <label for="pwd">계좌비밀번호</label>
			    <input type="password" name="password" class="form-control" placeholder="등록된PW입력" id="pwd">
			    <div id="pw-msg"></div>
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



<%@ include file="/WEB-INF/view/layout/footer.jsp" %>

<!-- footer -->