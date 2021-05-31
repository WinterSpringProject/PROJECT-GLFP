package glfp.glfp.service;

import glfp.glfp.domain.entity.Authority;
import glfp.glfp.domain.entity.Member;
import glfp.glfp.domain.repository.AuthorityRepository;
import glfp.glfp.domain.repository.MemberRepository;
import glfp.glfp.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public MemberDto getMember(Long mId) {

        Optional<Member> result = memberRepository.findById(mId);
        if (result.isPresent()) {
            Member member = result.get();
            return member.toDto(member);
        }
        return null;
    }

    @Transactional
    public MemberDto join(MemberDto memberDto) {
        if (memberRepository.findOneWithAuthoritiesByUsername(memberDto.getUsername()).orElse(null) != null) {
            return null;
        }

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .userPasswd(passwordEncoder.encode(memberDto.getUserPasswd()))
                .userEmail(memberDto.getUserEmail())
                .nickname(memberDto.getNickname())
                .userSex(memberDto.getUserSex())
                .activated(true)
                .build();

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .member(member)
                .build();

        memberRepository.save(member);
        authorityRepository.save(authority);
        return memberDto;
    }

    @Transactional
    public void revise(MemberDto memberDto) {
        Optional<Member> res = memberRepository.findById(memberDto.getId());
        try {
            res.ifPresent(m -> {
                Member member = res.get();
                member.setUsername(memberDto.getUsername());
                member.setUserEmail(memberDto.getUserEmail());
                member.setUserPasswd(memberDto.getUserPasswd());
                member.setNickname(memberDto.getNickname());
                member.setUserSex(memberDto.getUserSex());
            });
        } catch (Exception e) {

        }
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public String auth(String userEmail){
        AuthSogangService authSogangService = new AuthSogangService(emailService);
        try{
            return authSogangService.authSogang(userEmail);
        }catch(MessagingException e) {
            return null;
        }
    }

}
