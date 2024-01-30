package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.ManagerEntity;

import java.util.List;

public interface ManagerService {

    ManagerEntity createManager(long officeId, ManagerEntity manager);

    ManagerEntity getManagerByOfficeId(long officeId);

    List<ManagerEntity> getAllManagersByOfficeId(long officeId);

    ManagerEntity updateManager(long managerId, ManagerEntity manager);

    ManagerEntity partialUpdate(long managerId, ManagerEntity manager);

    void deleteManager(long managerId);

    void deleteAllManagersOfOffice(long officeId);

}
