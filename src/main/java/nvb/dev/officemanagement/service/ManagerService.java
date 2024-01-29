package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.ManagerEntity;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

    ManagerEntity createManager(ManagerEntity manager);

    ManagerEntity updateManager(long id, ManagerEntity manager);

    List<ManagerEntity> findAll();

    Optional<ManagerEntity> findManagerById(long id);

    boolean isExists(long id);

    ManagerEntity partialUpdate(long id, ManagerEntity manager);

    void deleteById(long id);

}
