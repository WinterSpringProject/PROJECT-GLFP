package glfp.glfp.controller;

import glfp.glfp.domain.entity.Member;
import glfp.glfp.dto.MemberDto;
import glfp.glfp.security.JwtTokenProvider;
import glfp.glfp.service.AuthSogangService;
import glfp.glfp.service.EmailService;
import glfp.glfp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{member_id}") //조회
    public ResponseEntity<MemberDto> getMember(@PathVariable("member_id") Long mId){
        MemberDto memberDTO = memberService.getMember(mId);
        if (memberDTO == null)
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PostMapping("") //등록 ->Post 방식이기 때문에 RequestBody를 통해 HTTP요청 body를 자바 객체로 받을 수 있다.
    public ResponseEntity<String> register(@RequestBody MemberDto memberDto){
        if (memberService.join(memberDto) == null)
            return new ResponseEntity<>("이미 존재하는 아이디 입니다.", HttpStatus.CONFLICT);
        return new ResponseEntity<>(memberDto.getUserName(), HttpStatus.OK);
    }

    @PutMapping("") //수정
    public ResponseEntity<String> revise(@RequestBody MemberDto memberDto){
        memberService.revise(memberDto);
        return new ResponseEntity<>(memberDto.getUserName(), HttpStatus.OK);
    }

    @DeleteMapping("/{member_id}") //삭제
    public ResponseEntity<String> delete(@PathVariable("member_id") Long mId){
        String userName = memberService.getMember(mId).getUserName();
        memberService.delete(mId);
        return new ResponseEntity<>(userName, HttpStatus.OK);
    }

    @GetMapping("/auth") //메일 인증
    public ResponseEntity<String> getAuthCode(@RequestBody HashMap<String,String> requestMap){
        String userEmail = requestMap.get("email");
        String authCode = memberService.auth(userEmail);
        if (authCode == null)
            return new ResponseEntity<>("잘못된 email 주소입니다.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }

    @PostMapping("/login") //로그인
    public ResponseEntity<String> login(@RequestBody MemberDto memberDto){
        MemberDto findDto = memberService.getMemberWithUserAccount(memberDto.getUserAccount());
        if (memberService.checkLoginValid(memberDto, findDto))
            return new ResponseEntity<>(jwtTokenProvider.createToken(findDto.getId(),
                    findDto.getUserName(), findDto.getRoles()), HttpStatus.OK);
        return new ResponseEntity<>("가입되지 않은 사용자 혹은 잘못된 비밀번호입니다.", HttpStatus.NOT_FOUND);
    }
}
