package glfp.glfp.dto;

import glfp.glfp.domain.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String nickname;
    private String userPasswd;
    private String username;
    private int userSex;
    private String userEmail;

    public Member toEntity(MemberDto memberDto){
        Member build = Member.builder()
                .id(memberDto.getId())
                .nickname(memberDto.getNickname())
                .userPasswd(memberDto.getUserPasswd())
                .username(memberDto.getUsername())
                .userSex(memberDto.getUserSex())
                .userEmail(memberDto.getUserEmail())
                .build();
        return build;
    }

}
