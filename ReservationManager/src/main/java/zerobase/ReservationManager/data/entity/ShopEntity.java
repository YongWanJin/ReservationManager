package zerobase.ReservationManager.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/** 상점 정보를 담은 엔티티 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "SHOP")
public class ShopEntity {
    /** 상점 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int shopId;

    /***/
}
