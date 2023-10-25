package zerobase.ReservationManager.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.ReservationManager.data.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findById(String id);
    boolean existsById(String id);
}
