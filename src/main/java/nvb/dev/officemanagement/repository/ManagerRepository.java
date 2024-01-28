package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
}
