package com.traveloper.tourfinder.auth.service;

import com.traveloper.tourfinder.auth.dto.VerifyCodeSendSuccessDto;
import com.traveloper.tourfinder.common.RedisRepo;
import com.traveloper.tourfinder.common.util.RandomCodeUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${STMP_USERNAME}")
    private static final String senderEmail = "";
    private final RedisRepo redisRepo;




    // 이메일로 인증코드 전송
    public VerifyCodeSendSuccessDto sendVerifyCodeMail(String email){
        MimeMessage message = javaMailSender.createMimeMessage();
        String randomCode = RandomCodeUtils.generate(6);
        try {
            message.setFrom(senderEmail);   // 보내는 이메일
            message.setRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
            message.setSubject("[Team ABC] 요청하신 인증코드 입니다.");  // 제목 설정
            String body = "";
            body += "<h2>" + "사용자가 본인임을 확인하려고 합니다. " + "</h2>";
            body += "<p>" + "이용중이던 웹 서비스로 돌아가 다음 확인 코드를 입력하세요. <br></br> <b>유효시간은 5분 입니다. </b>"  +  "</p>";

            body += "<h3>" + "인증 코드입니다." + "</h2>";
            body += "<h1 style='color:blue'>" + randomCode + "</h1>";
            body += "</div><br>";
            message.setText(body,"UTF-8", "html");
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
        return VerifyCodeSendSuccessDto.builder()
                .email(email)
                .code(randomCode)
                .build();

    }



    public void sendNotificationEmail(){

    }
}
