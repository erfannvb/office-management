package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.ClerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClerkRepository extends JpaRepository<ClerkEntity, Long> {

    Optional<ClerkEntity> findByOfficeIdAndManagerId(long officeId, long managerId);

    List<ClerkEntity> findByOfficeId(long officeId);

    List<ClerkEntity> findByManagerId(long managerId);

}
