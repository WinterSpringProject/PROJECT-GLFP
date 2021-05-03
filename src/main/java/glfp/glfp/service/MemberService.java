package glfp.glfp.service;

import glfp.glfp.domain.entity.Member;
import glfp.glfp.domain.repository.MemberRepository;
import glfp.glfp.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;


@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                         EmailService emailService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

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
    public Long join(MemberDto memberDto) {
        //TODO : 중복확인 버튼을 눌렀을 때 ID가 중복되었는지 확인하는 로직 필요 -> 현재는 회원가입 버튼을 눌렀을 때 로직 수행.
        //TODO : 비밀번호가 유효한 비밀번호인지 확인하는 로직 작성
        Optional<Member> findDto = memberRepository.findByUserAccount(memberDto.getUserAccount());
        if (findDto.isEmpty()) {
            MemberDto newMemberDto = MemberDto
                    .builder()
                    .id(memberDto.getId())
                    .userAccount(memberDto.getUserAccount())
                    .userPasswd(passwordEncoder.encode(memberDto.getUserPasswd()))
                    .userName(memberDto.getUserName())
                    .userSex(memberDto.getUserSex())
                    .userEmail(memberDto.getUserEmail())
                    .roles(Collections.singletonList("USER")).build();
            Member member = memberDto.toEntity(newMemberDto);
            memberRepository.save(member);
            return member.getId();
        }
        return null;
    }

    @Transactional
    public void revise(MemberDto memberDto) {
        Optional<Member> res = memberRepository.findById(memberDto.getId());
        try {
            res.ifPresent(m -> {
                Member member = res.get();
                member.setUserName(memberDto.getUserName());
                member.setUserEmail(memberDto.getUserEmail());
                member.setUserPasswd(memberDto.getUserPasswd());
                member.setUserAccount(memberDto.getUserAccount());
                memberRepository.save(member);

            });
        } catch (Exception e) {

        }
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberDto getMemberWithUserAccount(String userAccount) {
        Optional<Member> findDto = memberRepository.findByUserAccount(userAccount);
        if (findDto.isPresent()) {
            Member member = findDto.get();
            return member.toDto(member);
        }
        return null;
    }

    @Transactional
    public boolean checkLoginValid(MemberDto memberDto, MemberDto findDto) {
        if (findDto == null)
            return false;
        else{
            if(!passwordEncoder.matches(memberDto.getUserPasswd(), findDto.getUserPasswd()))
                return false;
        }
        return true;
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
