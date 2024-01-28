package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, Long> {
}
