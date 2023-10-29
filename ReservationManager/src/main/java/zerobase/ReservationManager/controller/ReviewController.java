package zerobase.ReservationManager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {

    /** 리뷰 작성 API */
    @GetMapping("/write")
    @PreAuthorize("hasRole('GUEST')")
    public String serach(){
        return "리뷰 작성 (미구현)";
        // 날씨 일기 강좌 다시 찾아보기
    }
}
