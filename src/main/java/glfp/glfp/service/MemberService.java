package glfp.glfp.service;

import glfp.glfp.domain.entity.Member;
import glfp.glfp.domain.repository.MemberRepository;
import glfp.glfp.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;

    }

    @Transactional
    public MemberDto getMember(Long mId){

        Optional<Member> result = memberRepository.findById(mId);
        if(result.isPresent()) {
            Member member = result.get();
            return member.toDto(member);
        }
        //없으면 null 반환
        return null;
    }

    @Transactional
    public Long join(MemberDto memberDto){
        Member member = memberDto.toEntity(memberDto);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void revise(MemberDto memberDto){
        Optional<Member> res = memberRepository.findById(memberDto.getId());
        try{
            res.ifPresent(m -> {
                Member member = res.get();
                member.setUserName(memberDto.getUserName());
                member.setUserEmail(memberDto.getUserEmail());
                member.setUserPasswd(memberDto.getUserPasswd());
                member.setUserAccount(memberDto.getUserAccount());
                memberRepository.save(member);

            });
        }catch(Exception e){

        }
    }

    @Transactional
    public void delete(Long id){
        memberRepository.deleteById(id);
    }


}
