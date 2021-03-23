package glfp.glfp.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;


@RequiredArgsConstructor
@Component
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendMail(String to, String authCode) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom("noreply@glfp.com");
        message.setSubject("GLFP 인증 메일입니다.");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setText(authCode);
        message.setSentDate(new Date());
        emailSender.send(message);
    }

}
