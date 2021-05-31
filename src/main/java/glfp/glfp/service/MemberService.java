package glfp.glfp.service;

import glfp.glfp.dto.MemberDto;

public interface MemberService {
    MemberDto getMember(Long mId);

    MemberDto join(MemberDto memberDto);

    void revise(MemberDto memberDto);

    void delete(Long id);

    String auth(String userEmail);
}
