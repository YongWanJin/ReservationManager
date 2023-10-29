package zerobase.ReservationManager.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/** 회원정보를 담고 있는 Entity
 * */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "MEMBER")
public class MemberEntity implements UserDetails {

    /** 회원 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /** 유저 아이디 (로그인용) */
    private String username;

    /** 성함, 이름 */
    private String username2;

    /** 휴대폰 번호 */
    private String phone;

    /** 비밀번호 */
    private String password;

    /** 회원 유형 : 손님, 점장 */
    private String role;
//    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(role));
        return auth;
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
