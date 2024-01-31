package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {

    List<ManagerEntity> findAllByOfficeId(long officeId);

    @Transactional
    void deleteByOfficeId(long officeId);

}
