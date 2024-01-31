package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.ManagerEntity;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

    ManagerEntity createManager(long officeId, ManagerEntity manager);

    Optional<ManagerEntity> getManagerById(long managerId);

    List<ManagerEntity> getAllManagersByOfficeId(long officeId);

    ManagerEntity updateManager(long managerId, ManagerEntity manager);

    ManagerEntity partialUpdate(long managerId, ManagerEntity manager);

    void deleteManager(long managerId);

    void deleteAllManagersOfOffice(long officeId);

    boolean isExists(long officeId);

}
