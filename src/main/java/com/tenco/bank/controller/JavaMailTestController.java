package com.tenco.bank.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenco.bank.dto.ResponseDto;


@RestController
public class JavaMailTestController {

    // ("/auth/send") Path를 통해 실습해봤다.
	
	// http://localhost:80/auth/send
	
    @GetMapping("/auth/send")
    public ResponseDto<Integer> mailSend(){
        return new ResponseDto<>(HttpStatus.OK.value(), naverMailSend());
    }

    public static int naverMailSend(){
        String host = "smtp.naver.com";
        String user = "afc2015@naver.com";
        String password = "비번입력";

        // SMTP 서버 정보를 설정한다.
        Properties props = new Properties(); // Properties는 java.util의 Properties입니다.
        props.put("mail.smtp.host", host); // smtp의 호스트
        props.put("mail.smtp.port", 587); // 587 포트 사용
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 이 설정을 안붙이면 TLS Exception이 뜨더라구요. (버전이 안맞아서)

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            // 수신자 이메일
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("afc2015@naver.com"));

            // 메일 제목
            message.setSubject("테스트메일");

            // 메일 내용
            message.setText("테스트용 메일입니다 Kira군 ❤❤");

            // send the message
            Transport.send(message);
            System.out.println("Success Message Send");
            return 0;
        }catch (MessagingException e){
            e.printStackTrace();
            return -1;
        }
    }
}


