package glfp.glfp.dto;

import glfp.glfp.domain.entity.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String userAccount;
    private String userPasswd;
    private String userName;
    private int userSex;
    private String userEmail;
    private List<String> roles;

    public Member toEntity(MemberDto memberDto){
        Member build = Member.builder()
                .id(memberDto.getId())
                .userAccount(memberDto.getUserAccount())
                .userPasswd(memberDto.getUserPasswd())
                .userName(memberDto.getUserName())
                .userSex(memberDto.getUserSex())
                .userEmail(memberDto.getUserEmail())
                .roles(memberDto.getRoles())
                .build();
        return build;
    }

    @Builder
    public MemberDto(Long id, String userAccount, String userPasswd, String userName, int userSex, String userEmail, List<String> roles) {
        this.id = id;
        this.userAccount = userAccount;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.userSex = userSex;
        this.userEmail = userEmail;
        this.roles = roles;
    }

}
