package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 1. HandlerInterceptor 구현하기
// AuthInterceptor --> new -->


@Component // IoC 대상
public class AuthInterceptor implements HandlerInterceptor {
	
	
	// preHandle -- > 하나의 request 요청을 Controller 로 보낼지 말지 결정할 수 있음
	// -> Controller 들어오기 전 얘 가 걸러주는 역할을 수행함!
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("인터셉터 동작확인 - 111111111111111111111111111111111111");
		// 인증검사
		 HttpSession session = request.getSession();
		 User principal	= (User)session.getAttribute(Define.PRINCIPAL);
		 
		 if(principal == null) {
			 //response.sendRedirect("/user/sign-in");
			 
			 // 예외처리
			 throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
 
		 }
		 
		// boolean 타입이기 때문에 true 를 return 해주면 됨.
		return true;
	}
	
	
	
	
	
	
	
	// 뷰가 렌더링 되기 전에 호출되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
	
	
	
	// 요청처리가 완료 된 후, 뷰 렌더링이 완료된 후 호출된다.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
}
