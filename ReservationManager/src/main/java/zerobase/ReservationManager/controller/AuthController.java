package zerobase.ReservationManager.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.ReservationManager.data.dto.Auth;
import zerobase.ReservationManager.data.entity.MemberEntity;
import zerobase.ReservationManager.security.TokenProvider;
import zerobase.ReservationManager.service.MemberService;

import java.util.ArrayList;

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
        System.out.println("sign up start");
        MemberEntity result = this.memberService.register(request);
        System.out.println("sign up complete");
        return ResponseEntity.ok(result);
        // 왜 Entity를 반환하는가? DTO 객체를 반환해야하지 않나?
    }

    /** 로그인 API */
    @PostMapping("/signin")
    public ResponseEntity<?> singin(@RequestBody Auth.SignIn request){
        // 아이디와 패스워드가 일치하는지 확인
        MemberEntity member = this.memberService.authenticate(request);
        // 토큰 생성 및 반환
        ArrayList<String> role = new ArrayList<>();
        role.add(member.getRole());
        String token = this.tokenProvider.generateToken(member.getUsername(), role);
        return ResponseEntity.ok(token);
    }

    /** 로그아웃 API */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "로그아웃 완료";
        // https://velog.io/@minwest/Spring-Security-jwt로-로그인로그아웃-구현하기
        // 제로베이스 강의 레디스 캐시 서버 구축 선행할것.
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('STAFF')")
    public String test(){
        return "test complete! You are Staff.";
    }
}
