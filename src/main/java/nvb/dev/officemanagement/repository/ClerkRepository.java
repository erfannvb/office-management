package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.ClerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClerkRepository extends JpaRepository<ClerkEntity, Long> {

    List<ClerkEntity> findByOfficeIdAndManagerId(long officeId, long managerId);

    @Transactional
    void deleteByManagerId(long managerId);

}
