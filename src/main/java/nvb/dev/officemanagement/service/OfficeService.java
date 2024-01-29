package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

    OfficeEntity createOffice(OfficeEntity office);

    OfficeEntity updateOffice(long id, OfficeEntity office);

    List<OfficeEntity> findAll();

    Optional<OfficeEntity> findOfficeById(long id);

    boolean isExists(long id);

    OfficeEntity partialUpdate(long id, OfficeEntity office);

    void deleteById(long id);

}
