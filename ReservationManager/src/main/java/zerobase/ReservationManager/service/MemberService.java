package zerobase.ReservationManager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.ReservationManager.data.dto.Auth;
import zerobase.ReservationManager.data.entity.MemberEntity;
import zerobase.ReservationManager.data.repository.MemberRepository;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));
    }

    /** 회원 가입 기능 : 새로운 회원 정보를 데이터베이스에 저장한다.*/
    public MemberEntity register(Auth.SingUp member) {
        // 아이디 중복 여부 확인
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if(exists){
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }

        // 패스워드 암호화
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        // 입력한 정보(member) dto 객체를 Entity로 변환시켜 DB에 저장
        MemberEntity result = this.memberRepository.save(member.toEntity());

        // 결과 반환
        return result;
    }

    /** 로그인을 위한 인증 */
    public MemberEntity authenticate(Auth.SignIn member) {
        // 아이디 비교
        MemberEntity user = this.memberRepository.findByUsername(member.getUsername())
                .orElseThrow(()->new RuntimeException("해당 아이디가 존재하지 않습니다."));

        // 패스워드 비교
        if(!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공
        return user;
    }
}
