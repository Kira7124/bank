package com.tenco.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.dto.TransferFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.entity.Account;
import com.tenco.bank.repository.entity.CustomHistory;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;





@Controller // 데이터 를 (만) 반환하고싶으면 -> @RestController 달아주기
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	// ++생성자 의존주입 -> DI (직접 만들어도 됨)
	
	@Autowired
	private AccountService accountService;
	// ++생성자 의존주입 -> DI (직접 만들어도 됨)
	
	@Autowired
	private UserService userService;
	
	
	// 주소설계
	// http://localhost:80/account/save

	
	
	
	// 페이지 요청
	@GetMapping("/save")
	public String savePageGet() {
		// 인증검사
		
		/**
		 * 계좌생성페이지요청
		 * @return saveForm.jsp
		 */
		
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		
		
		return "account/saveForm";
	}
	
	
	/**
	 * 계좌생성 처리
	 * @param dto
	 * @return list.jsp
	 */
	
	
	
	@PostMapping("/save")
	public String savePagePost(AccountSaveFormDto dto) {
		
		
		
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getBalance() < 0) {
			throw new CustomRestfulException("잘못된 금액입니다!", HttpStatus.BAD_REQUEST);
		}
		
		
		//DTO 작성 
		
		//Repository 작성 후 account.xml mapper id 참고하기
		
		//Service 작성
		
		//Service 호출후 처리 (계좌생성처리)
		
		// ++ 유효성 검사 처리달아주기!!(if 문으로 작성)
		// ++ 인증검사 
		// ++ 서비스 호출
		// ++ 응답처리 
		// +++ 파싱? -> (DTO 방식으로)
		// todo -> 수정예정...
		
		accountService.createAccount(dto, principal.getId());
		return "redirect:/account/list";
		
	}
	
	//계좌 목록 보기페이지 생성
	//주소설계
	// http://localhost:80/account/list
	// http://localhost:80/accoutn/
	
	
	
	/**
	 * 계좌목록페이지호출
	 * @param model - accountList
	 * @return list.jsp
	 */
	
	
	@GetMapping( {"/list", "/"})
	public String listPage(Model model) {
		
		// 1.인증검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		

		// ++ 경우의 수 -> 있거나 없거나 둘 중 한개 설정 (model) 
		List<Account> accountList = accountService.readAccountListByUserId(principal.getId());
		
		
		
		if(accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		}else {
			model.addAttribute("accountList", accountList);
		}
		
		return "account/list";
	}
	
	
	
	// 출금페이지 요청
	// 주소설계
	// http://localhost:80/account/withdraw
	
	
	// 출금
	@GetMapping("/withdraw")
	public String withdrawPageGet() {
		
			
		
		return "account/withdraw";
	}
	
	
	// 출금요청 로직만들기 
	// 파싱전략 -> dto 활용
	
	
	
	@PostMapping("/withdraw")
	public String withdrawPagePost(WithdrawFormDto dto) {
		
		// 인증검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		// 금액입력안했을때
		if(dto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		// <= 0 일때 
		if(dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("출금금액이 0원 이하일 수 없습니다!", HttpStatus.BAD_REQUEST);
		}
		
		
		// 계좌번호확인 (앞에 null 이면 뒤에걸로 안넘어감 / not null 이면 뒤에걸로 실행)
		if(dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		// 계좌비밀번호확인 (앞에 null 이면 뒤에걸로 안넘어감 / not null 이면 뒤에걸로 실행)
		if(dto.getWAccountPassword() == null || dto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		
		
		
		//서비스호출 -> 사용자가 request 한 dto 필요 ! 
		// principal.getId() -> 인증검사를 위한 id 값 필요!
		accountService.updateAccountWithdraw(dto, principal.getId());
		
		return "redirect:/account/list";
	}

	
	
	
	
	
	// 입금
	@GetMapping("/deposit")
	public String depositpageGET() {
		
		

		
		return "account/deposit";
		
	}
	
	
	
	
	
	@PostMapping("/deposit")
	public String depositpagePOST(DepositFormDto dto) {
		
		// 인증검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}

		// 금액입력안했을때
		if (dto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하세요!", HttpStatus.BAD_REQUEST);
		}

		// <= 0 일때
		if (dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("입금금액이 0원 이하일 수 없습니다!", HttpStatus.BAD_REQUEST);
		}

		// 계좌번호확인 (앞에 null 이면 뒤에걸로 안넘어감 / not null 이면 뒤에걸로 실행)
		if (dto.getDAccountNumber() == null || dto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}

		// 계좌비밀번호확인 (앞에 null 이면 뒤에걸로 안넘어감 / not null 이면 뒤에걸로 실행)
		if (dto.getDAccountPassword() == null || dto.getDAccountPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
	
		accountService.updateAccountDeposit(dto,principal.getId());
		
		return "redirect:/account/list";
		
		
	}
	

	
	
	
	
	@GetMapping("/transfer")
	public String transferpageGET() {
		
		

		
		return "account/transfer";
		
	}
	
	
	
	
	@PostMapping("/transfer")
	public String transferpagePOST(DepositFormDto ddto, WithdrawFormDto wdto) {
		
		
		// 인증검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		// 금액입력안했을때
		if (wdto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하세요!", HttpStatus.BAD_REQUEST);
		}

		// <= 0 일때
		if (wdto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("송금금액이 0원 이하일 수 없습니다!", HttpStatus.BAD_REQUEST);
		}

		// 계좌번호확인 (앞에 null 이면 뒤에걸로 안넘어감 / not null 이면 뒤에걸로 실행)
		if (ddto.getDAccountNumber() == null || ddto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요!", HttpStatus.BAD_REQUEST);
		}
		
		
		
		accountService.transferAccount(ddto, wdto, principal.getId());
		return "redirect:/account/list";
	}
	
	
	
	// 계좌 상세보기 페이지 -- 전체 , 입금 , 출금
	// 주소설계
	// http:/localhost:80/account/detail
	// http:/localhost:80/account/detail/1?type=
	
	
	
	
	
	
	@GetMapping("/detail/{id}")
	public String detailpageGET(@PathVariable Integer id,
			@RequestParam(name ="type", defaultValue = "all", required = false) String type, Model model) {
	
		
		
		System.out.println("type : " + type);
		
		
		// 인증검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 이 필요합니다!", HttpStatus.UNAUTHORIZED);
		}
		
		
		
		
		//서비스 호출
		
		Account account = accountService.detailAccount(id);
		List<CustomHistory> historylist =  accountService.readHistoryListByAccount(type,id);
		
		System.out.println("list :" + historylist.toString());
		
		model.addAttribute("account", account);
		model.addAttribute("historyList", historylist);
		
		//서비스 호출
		
		
		
		return "account/detail";
	}
	
	
	
	
	
	
	
	
	
	
	

	
}
