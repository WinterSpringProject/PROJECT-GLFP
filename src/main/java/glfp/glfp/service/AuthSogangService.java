package glfp.glfp.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.mail.MessagingException;
import java.util.Random;

@RequiredArgsConstructor
public class AuthSogangService {

    private final EmailService emailService;

    public String authSogang(String email) throws MessagingException {
        if(email.contains("@sogang.ac.kr")) {
            String authCode = this.generateAuthCode();
            emailService.sendMail(email, authCode);
            return authCode;
        }
       else
            return null;
    }

    public String generateAuthCode() {
        StringBuffer authCode = new StringBuffer();
        authCode.setLength(0);
        Random random = new Random();
        authCode.append(random.nextInt(900000) + 100000);

        return authCode.toString();
    }
}
