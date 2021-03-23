package glfp.glfp.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        //로그인한 사람이 누구인지.
        session.setAttribute("userAccount", authentication.getName());  //세션에 user ID를 저장.
        System.out.println("onSession : " + session.getAttribute("userAccount"));   //session 등록 확인
        response.sendRedirect("/main"); //로그인 성공시 /main으로 보낸다
    }
}
