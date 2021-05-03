package glfp.glfp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request); //헤더로부터 JWT를 받아옴
        if (token != null && jwtTokenProvider.validateToken(token)){ //유효한지 확인
            Authentication authentication = jwtTokenProvider.getAuthentication(token); //토큰으로부터 유저 정보
            SecurityContextHolder.getContext().setAuthentication(authentication); //SecurityContext에 객체 저장
        }
        chain.doFilter(request, response);
    }
}
