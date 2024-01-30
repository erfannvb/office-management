package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.List;
import java.util.Optional;

public interface OfficeService {

    OfficeEntity createOffice(OfficeEntity office);

    List<OfficeEntity> getAllOffices();

    Optional<OfficeEntity> getOfficeById(long officeId);

    Optional<OfficeEntity> getOfficeByOfficeName(String officeName);

    Optional<OfficeEntity> getOfficeByOfficeCode(String officeCode);

    OfficeEntity updateOffice(long officeId, OfficeEntity office);

    OfficeEntity partialUpdate(long officeId, OfficeEntity office);

    void deleteOffice(long officeId);

    void deleteAllOffices();


}
