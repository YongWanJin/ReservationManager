package zerobase.ReservationManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("reserve")
@RequiredArgsConstructor
public class ReservationController {

    /** 매장 검색 및 상세 정보 확인 API */
    @GetMapping("/search")
//    @PreAuthorize("hasRole('GUEST')")
    public String serach(){
        return "매장 검색 및 상세 정보 확인 (미구현)";
    }

    /** 예약 (예약 가능 여부 확인 후 예약 진행) API */
    @GetMapping("/reserve")
    @PreAuthorize("hasRole('GUEST')")
    public String reserve(){
        return "예약 (미구현)";
    }


}
