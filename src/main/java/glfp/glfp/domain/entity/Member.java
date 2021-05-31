package glfp.glfp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import glfp.glfp.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@ToString(of = {"id", "userPasswd", "username", "userSex", "userEmail", "nickname", "activated"})
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String userPasswd;

    @Column(nullable = false)
    private int userSex;

    @Column(length = 20, nullable = false)
    private String userEmail;

    @Column(length = 20, nullable = false)
    private String nickname;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    public MemberDto toDto(Member member){
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .userPasswd(member.getUserPasswd())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .userSex(member.getUserSex())
                .userEmail(member.getUserEmail())
                .build();
        return memberDto;
    }

}
