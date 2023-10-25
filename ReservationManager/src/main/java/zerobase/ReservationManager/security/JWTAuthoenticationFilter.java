package zerobase.ReservationManager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** 최초로 들어온 리퀘스트로부터 jwt 토큰을 판별 및 유효성 검사를 하기 위한 클래스*/
@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthoenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "Authorization"; // 어떤 헤더를 이용할지
    public static final String TOKNE_PERFIX = "Bearer "; // 인증 타입

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토큰이 맞는지 검사 후 토큰 값 추출
        String token = this.resolveTokenFromRequest(request);
        // 토큰이 유효한지 검사
        if(StringUtils.hasText(token) && this.tokenProvider.validateToken(token)){
            Authentication auth = this.tokenProvider.getAuthetication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    /** request에 있는 헤더로부터 토큰 추출하기 */
    private String resolveTokenFromRequest(HttpServletRequest request){
        // 토큰 헤더에 들어있는 값을 추출
        String token = request.getHeader(TOKEN_HEADER);
        // 해당 값이 토큰의 형태를 갖췄는지 판별
        if(!ObjectUtils.isEmpty(token) && token.startsWith(TOKNE_PERFIX)){
            // 실제 토큰 부위에 해당하는 값을 리턴
            return token.substring(TOKNE_PERFIX.length());
        }
        return null;
    }
}
