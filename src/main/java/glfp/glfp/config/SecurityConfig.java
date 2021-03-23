package glfp.glfp.config;

import glfp.glfp.security.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf() //csrf 토큰 검사 비활성화
                    .disable()
                .authorizeRequests()    //Access 제한 허용
                    .antMatchers("/login","/swagger-ui.html#/**","/member","/member/auth")   //control할 url 지정
                        .permitAll()        // /login으로 접근하는 모든 사용자에 대해 접근 허용
                    .anyRequest()           // antMatcher로 지정한 url 이외의 모든 url에 대해
                    .authenticated()        // 인증된 사용자만 접근할 수 있도록 함. -> 특정 url 설정 모두 처리후 제일 마지막에 사용할 것!
                .and()
                .formLogin()    //form을 통한 로그인 활성화.
                    //.loginPage("/login")   //login이 필요한 페이지에 접근했을시, 사용자에게 보여줄 페이지 url 설정
                    .loginProcessingUrl("/doLogin")     //post로 로그인한 정보를 전달할 url
                    .usernameParameter("userAccount")   //form 전송시 사용할 parameter의 key값
                    .passwordParameter("userPasswd")
                    .successHandler(new LoginSuccessHandler())
                    .and()
                .logout()
                    .logoutUrl("/doLogout") //클라이언트에서 로그아웃을 요청할 때 사용하는 url
                    .logoutSuccessUrl("/login");    //로그아웃 성공시 /login으로 redirect

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
