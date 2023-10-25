package zerobase.ReservationManager.data.dto;

import lombok.Data;
import zerobase.ReservationManager.data.entity.MemberEntity;

import java.util.List;

/** 회원 가입과 인증을 위한 dto 객체 */
public class Auth {

    /** 로그인시 필요한 정보들 */
    @Data
    public static class SignIn{
        private String id;
        private String password;
    }

    /** 회원 가입시 필요한 정보들 */
    @Data
    public static class SingUp{
        private String id;
        private String username;
        private String phone;
        private String password;
        private List<String> roles;

        /** dto 객체에서 Entity 객체로 변환하는 메서드 */
        public MemberEntity toEntity(){
            return MemberEntity.builder()
                    .id(this.id)
                    .username(this.username)
                    .phone(this.phone)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }

    }
}
