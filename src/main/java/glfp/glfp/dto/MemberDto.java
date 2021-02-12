package glfp.glfp.dto;

import glfp.glfp.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Member toEntity(MemberDto memberDto){
        Member build = Member.builder()
                .id(memberDto.getId())
                .userAccount(memberDto.getUserAccount())
                .userPasswd(memberDto.getUserPasswd())
                .userName(memberDto.getUserName())
                .userSex(memberDto.getUserSex())
                .userEmail(memberDto.getUserEmail())
                .build();
        return build;
    }

    @Builder
    public MemberDto(Long id, String userAccount, String userPasswd, String userName, int userSex, String userEmail) {
        this.id = id;
        this.userAccount = userAccount;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.userSex = userSex;
        this.userEmail = userEmail;
    }

    //private LocalDateTime createdTime;
}
