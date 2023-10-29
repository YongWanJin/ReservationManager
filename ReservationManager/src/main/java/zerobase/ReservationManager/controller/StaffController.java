package zerobase.ReservationManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("staff")
@RequiredArgsConstructor
public class StaffController {

    /** 상점 등록 API */
    @PostMapping("/regist")
    @PreAuthorize("hasRole('STAFF')")
    public String regist(){
        return "상점 등록 (미구현)";
        // 일대다 어떻게 적용하는지 찾아보기
    }

    /** 자신의 상점 예약 목록 확인 API */
    @PostMapping("/my-shop-reservation")
    @PreAuthorize("hasRole('STAFF')")
    public String myShopReservation(){
        return "자신의 상점 예약 목록 확인 (미구현)";
        // 여러개 출력하는 방식 찾아보기
    }
}
