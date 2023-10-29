package zerobase.ReservationManager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zerobase.ReservationManager.service.MemberService;

import java.util.Date;
import java.util.List;

/** 토큰을 발급하고 유효성 검사를 하기 위한 클래스*/
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간
    private final MemberService memberService;

    @Value("${sprnig.jwt.secret}")
    private String secretKey;

    /** 사용자 식별을 위한 jwt 토큰을 발급하기 */
    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        // 만료시간 설정
        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        // jwt 토큰 생성
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(expiredDate) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey) // 사용할 암호화 알고리즘, 비밀키
                .compact();
    }

    private String getId(String token){
        return this.parseClaims(token).getSubject();
    }

    /** 토큰이 유효한지 검사하기 */
    public boolean validateToken(String token){
        // 토큰의 값이 빈값임
        if(!StringUtils.hasText(token)){
            return false;
        }
        // 토큰 만료 시간에 따라 유효성 검사
        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    /** jwt 토큰으로부터 인증 정보를 가져오기 */
    public Authentication getAuthetication(String jwt){
        UserDetails userDetails = this.memberService.loadUserByUsername(this.getId(jwt));
        // 사용자 정보와 사용자 권한이 담긴 객체를 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /** 클레임 claim 정보 가져오기 */
    private Claims parseClaims(String token){
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
