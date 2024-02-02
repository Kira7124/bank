package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;

//@Configuration 
//IoC 대상이됨 : 2개 이상에 IoC 처리를 할 때 , 즉 Bean 객체를 만들 때 사용한다. 
// --> Spring Boot 설정클래스이다.


@Configuration 
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired // DI
	private AuthInterceptor authInterceptor;
	
	//요청이 올 때 마다 domain URI 검사를 할 예정
	//  /account/xxx -> 으로 들어오는 Domain 을 다 검사해 !!!
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account/**")
				.addPathPatterns("/auth/**");
				
		
		WebMvcConfigurer.super.addInterceptors(registry);
	} 
	
	
	
}
