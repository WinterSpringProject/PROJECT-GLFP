package glfp.glfp.security.service;

import glfp.glfp.domain.entity.Member;
import glfp.glfp.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO : 로그인 과정을 DB와 연결하기 위해 구현해야 함.

        Member user = memberRepository.findByUserAccount(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return new User(user.getUserAccount(),user.getUserPasswd(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }
}
