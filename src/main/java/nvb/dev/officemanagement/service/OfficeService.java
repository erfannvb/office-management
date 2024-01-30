package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.List;

public interface OfficeService {

    OfficeEntity createOffice(OfficeEntity office);

    List<OfficeEntity> getAllOffices();

    OfficeEntity getOfficeById(long officeId);

    OfficeEntity getOfficeByOfficeName(String officeName);

    OfficeEntity getOfficeByOfficeCode(String officeCode);

    OfficeEntity updateOffice(long officeId, OfficeEntity office);

    OfficeEntity partialUpdate(long officeId, OfficeEntity office);

    void deleteOffice(long officeId);

    void deleteAllOffices();


}
