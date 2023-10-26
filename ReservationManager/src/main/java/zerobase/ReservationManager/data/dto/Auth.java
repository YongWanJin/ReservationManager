package zerobase.ReservationManager.data.dto;

import lombok.Data;
import zerobase.ReservationManager.data.entity.MemberEntity;

import java.util.List;

/** 회원 가입과 인증을 위한 dto 객체 */
public class Auth {

    /** 로그인시 필요한 정보들 */
    @Data
    public static class SignIn{
        /** 회원 아이디 */
        private String username;
        /** 계정 비밀번호 */
        private String password;
    }

    /** 회원 가입시 필요한 정보들 */
    @Data
    public static class SingUp{
        /** 유저 아이디 (로그인용) */
        private String username;

        /** 성함, 이름 */
        private String username2;

        /** 휴대전화 번호 */
        private String phone;

        /** 계정 비밀번호 */
        private String password;

        /** 회원 유형 : 손님, 점장  */
        private String role;

        /** dto 객체에서 Entity 객체로 변환하는 메서드 */
        public MemberEntity toEntity(){
            return MemberEntity.builder()
                    .username(this.username)
                    .username2(this.username2)
                    .phone(this.phone)
                    .password(this.password)
                    .role(this.role)
                    .build();
        }

    }
}
