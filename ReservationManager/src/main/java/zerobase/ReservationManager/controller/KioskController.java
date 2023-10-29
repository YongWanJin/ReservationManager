package zerobase.ReservationManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {

    /** 키오스크에서 도착 확인 API */
    @GetMapping("/check")
    @PreAuthorize("hasRole('GUEST')")
    public String check(){
        return "키오스크에서 도착 확인 (미구현)";
    }
}
