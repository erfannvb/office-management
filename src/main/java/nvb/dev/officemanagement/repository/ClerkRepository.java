package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.ClerkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClerkRepository extends JpaRepository<ClerkEntity, Long> {
}
