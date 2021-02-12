package glfp.glfp.service;

import glfp.glfp.domain.entity.Member;
import glfp.glfp.domain.repository.MemberRepository;
import glfp.glfp.dto.MemberDto;
import org.springframework.stereotype.Service;

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
        Member member = memberRepository.findById(mId).get();
        return member.toDto(member);
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
