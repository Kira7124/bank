package com.tenco.bank.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tenco.bank.dto.Todo;



@RestController
public class RestControllerTest {
	
	
	// 카카오 , 공공API 등을 사용하기위해서 쓰는 컨트롤러
	//클라이언트에서 접근하는 주소 설계
	@GetMapping("/my-test1")
		public ResponseEntity<String> myTest1() {
			
			// 여기서 다른 서버로 자원을 요청한 다음,
			// 다시 클라이언트에게 자원을 내려주자.
		
			// 먼저 URI 객체 만들기
			URI uri = UriComponentsBuilder
					.fromUriString("https://jsonplaceholder.typicode.com")
					.path("/todos")
					.encode()
					.build()
					.toUri();
			
			
			RestTemplate restTemplate = new RestTemplate();
			
			// HTTP 통신 --> HTTP 메세지 헤더, 바디 를 각각 구성해서 보내야함.
			
			
			//헤더구성
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json; charset=UTF-8");
				
			
			//바디구성
			// 바디 구성
			MultiValueMap<String, String> params = new LinkedMultiValueMap();
			params.add("title", "진수의블로그");
			params.add("body", "글1번");
			params.add("userId", "2");
			
			// 헤더와 바디 결합 
			HttpEntity<MultiValueMap<String, String>> requestMessage 
				= new HttpEntity<>(params, headers);
			
			// HTTP 요청 처리 
			ResponseEntity<String> response 
					=  restTemplate.exchange(uri, HttpMethod.POST, requestMessage, String.class);
			
			
			
			
			// http://localhost:80/my-test1
			System.out.println("headers " + response.getHeaders());
			return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
			
			
			
			
			
			
			
		}
	
	
		//실습2 --> 우리서버에서 요청해서 응답을 받음. 
		// http://localhost:80/todos/3
	
		@GetMapping("/todos/{id}")
		public ResponseEntity<?> Test2(@PathVariable Integer id){
			
		//	URI uri = new URI("https://jsonplaceholder.typicode.com/" + id);
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com")
				.path("/todos")
				.path("/" + id)
				.encode()
				.build()
				.toUri();
			
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Todo> response = 
		restTemplate.getForEntity(uri, Todo.class); //GET 방식으로 요청 
		
			System.out.println(response.getHeaders());
			System.out.println(response.getBody());
			System.out.println(response.getBody().getTitle());
			
			return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
			
		}
		
		
		
		
		
		
		
		
		
		
		
		// 예제 https://jsonplaceholder.typicode.com/guide/
		
		  @GetMapping("/posts/{id}")
		    public ResponseEntity<?> getPost(@PathVariable Integer id) {
		        // GET 요청을 처리하는 로직을 구현하세요.
		        return ResponseEntity.ok("GET 요청 처리 결과");
		    }

		  
		  
		  
		  
		    @PostMapping("/posts/{id}")
		    public ResponseEntity<?> createPost(@PathVariable Integer id) {
		        // POST 요청을 처리하는 로직을 구현하세요.

		        URI uri = UriComponentsBuilder
		                .fromUriString("https://jsonplaceholder.typicode.com")
		            	.path("/posts/{id}")
		            	.buildAndExpand(id)
		                .toUri();

		        RestTemplate restTemplate = new RestTemplate();

		        HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-type", "application/json; charset=UTF-8");

		        Map<String, Object> requestBody = new HashMap<>();
		        requestBody.put("id", id);
		        requestBody.put("title", "foo");
		        requestBody.put("body", "bar");
		        requestBody.put("userId", 1);

		        HttpEntity<Map<String, Object>> requestMessage = new HttpEntity<>(requestBody, headers);

		        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestMessage, String.class);

		        // POST 요청의 결과를 처리하고 응답
		        if (response.getStatusCode().is2xxSuccessful()) {
		            return ResponseEntity.status(response.getStatusCode()).body("POST 요청 처리 결과");
		        } else {
		            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		        }
		    }
		
		
		
		
		
		@PutMapping("/posts/{id}")
		public ResponseEntity<?> Test4(@PathVariable Integer id){
			
		// http://localhost:80/posts/1
			
			
			
			URI uri = UriComponentsBuilder
					.fromUriString("https://jsonplaceholder.typicode.com")
					.path("/posts/{id}")
					.buildAndExpand(id)
					.encode()
					.toUri();
			
		RestTemplate restTemplate = new RestTemplate();
		
		
		//헤더구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json; charset=UTF-8");
		
		
		//바디구성
		Map<String, Object> requestBody = new HashMap();
		requestBody.put("id", id);
		requestBody.put("title", "foo2");
		requestBody.put("body", "bar2");
		requestBody.put("userId", 1);
		
		
		// 헤더와 바디 결합 
		HttpEntity<Map<String, Object>> requestMessage = new HttpEntity<>(requestBody, headers);
		
			
			
		// HTTP 요청 처리
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, requestMessage, String.class);

		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
			
	}
		
		
		
		
	
	
	
	
}
