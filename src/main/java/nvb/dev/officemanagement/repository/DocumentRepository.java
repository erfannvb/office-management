package nvb.dev.officemanagement.repository;

import nvb.dev.officemanagement.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    List<DocumentEntity> findAllByOfficeId(long officeId);

    @Transactional
    void deleteByOfficeId(long officeId);

}
