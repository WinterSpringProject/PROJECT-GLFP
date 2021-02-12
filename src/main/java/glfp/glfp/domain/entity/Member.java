package glfp.glfp.domain.entity;

import glfp.glfp.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String userAccount;
    @Column(length = 20, nullable = false)
    private String userPasswd;
    @Column(length = 15, nullable = false)
    private String userName;
    @Column(nullable = false)
    private int userSex;
    @Column(length = 20, nullable = false)
    private String userEmail;

    @Builder
    public Member (Long id,String userAccount, String userPasswd, String userName,int userSex, String userEmail){
        this.id = id;
        this.userAccount = userAccount;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.userSex = userSex;
        this.userEmail = userEmail;
    }

    public MemberDto toDto(Member member){
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .userAccount(member.getUserAccount())
                .userPasswd(member.getUserPasswd())
                .userName(member.getUserName())
                .userSex(member.getUserSex())
                .userEmail(member.getUserEmail())
                .build();
        return memberDto;
    }
}
