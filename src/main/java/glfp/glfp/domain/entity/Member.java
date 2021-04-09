package glfp.glfp.domain.entity;

import glfp.glfp.dto.MemberDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String userAccount;
    @Column(length = 100, nullable = false)
    private String userPasswd;
    @Column(length = 15, nullable = false)
    private String userName;
    @Column(nullable = false)
    private int userSex;
    @Column(length = 20, nullable = false)
    private String userEmail;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
//    @Column(nullable = false)
//    private String role;

    public MemberDto toDto(Member member){
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .userAccount(member.getUserAccount())
                .userPasswd(member.getUserPasswd())
                .userName(member.getUsername())
                .userSex(member.getUserSex())
                .userEmail(member.getUserEmail())
                .roles(member.getRoles())
                .build();
        return memberDto;
    }
    /*
    * UserDetails를 상속받기 위해 필수로 선언해야하는 아래 함수들
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userPasswd;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public Member (Long id,String userAccount, String userPasswd, String userName,int userSex, String userEmail, List<String> roles){
        this.id = id;
        this.userAccount = userAccount;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.userSex = userSex;
        this.userEmail = userEmail;
        this.roles = roles;
    }


}
