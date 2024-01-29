package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.service.ManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    @Override
    public ManagerEntity createUpdateManager(long id, ManagerEntity manager) {
        manager.setId(id);
        return managerRepository.save(manager);
    }

    @Override
    public List<ManagerEntity> findAll() {
        List<ManagerEntity> managerEntityList = managerRepository.findAll();
        if (managerEntityList.isEmpty()) throw new NoDataFoundException();
        return managerEntityList;
    }

    @Override
    public Optional<ManagerEntity> findManagerById(long id) {
        return Optional.ofNullable(managerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ManagerEntity.class, id)));
    }

    @Override
    public boolean isExists(long id) {
        return managerRepository.existsById(id);
    }

    @Override
    public ManagerEntity partialUpdate(long id, ManagerEntity manager) {
        manager.setId(id);

        return managerRepository.findById(id).map(existingManager -> {

            Optional.ofNullable(manager.getFirstName()).ifPresent(existingManager::setFirstName);
            Optional.ofNullable(manager.getLastName()).ifPresent(existingManager::setLastName);
            Optional.ofNullable(manager.getDepartment()).ifPresent(existingManager::setDepartment);
            Optional.ofNullable(manager.getAge()).ifPresent(existingManager::setAge);

            return managerRepository.save(manager);

        }).orElseThrow(() -> new EntityNotFoundException(ManagerEntity.class, id));
    }

    @Override
    public void deleteById(long id) {
        managerRepository.deleteById(id);
    }
}
