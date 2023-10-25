package zerobase.ReservationManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.ReservationManager.data.dto.Auth;
import zerobase.ReservationManager.data.entity.MemberEntity;
import zerobase.ReservationManager.security.TokenProvider;
import zerobase.ReservationManager.service.MemberService;

/** 로그인, 회원가입, 인증 관련 컨트롤러 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    /** 회원 가입 API */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SingUp request){
        MemberEntity result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    /** 로그인 API */
    @PostMapping("/signin")
    public ResponseEntity<?> singin(@RequestBody Auth.SignIn request){
        // 아이디와 패스워드가 일치하는지 확인
        MemberEntity member = this.memberService.authenticate(request);
        // 토큰 생성 및 반환
        String token = this.tokenProvider.generateToken(member.getId(), member.getRoles());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('STAFF')")
    public String test(){
        return "test complete! You are Staff.";
    }
}
