package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.ManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final OfficeRepository officeRepository;

    @Override
    public ManagerEntity createManager(long officeId, ManagerEntity manager) {
        OfficeEntity officeEntity = unwrapOffice(officeRepository.findById(officeId), officeId);
        manager.setOffice(officeEntity);
        return managerRepository.save(manager);
    }

    @Override
    public Optional<ManagerEntity> getManagerById(long managerId) {
        return managerRepository.findById(managerId);
    }

    @Override
    public List<ManagerEntity> getAllManagersByOfficeId(long officeId) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(officeId);
        if (optionalOffice.isPresent()) {
            return managerRepository.findAllByOfficeId(officeId);
        } else {
            throw new EntityNotFoundException(OfficeEntity.class, officeId);
        }
    }

    @Override
    public ManagerEntity updateManager(long managerId, ManagerEntity manager) {
        Optional<ManagerEntity> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isPresent()) {

            ManagerEntity currentManager = optionalManager.get();

            currentManager.setId(manager.getId());
            currentManager.setFirstName(manager.getFirstName());
            currentManager.setLastName(manager.getLastName());
            currentManager.setDepartment(manager.getDepartment());
            currentManager.setAge(manager.getAge());

            return managerRepository.save(currentManager);

        } else {
            throw new EntityNotFoundException(ManagerEntity.class, managerId);
        }
    }

    @Override
    public ManagerEntity partialUpdate(long managerId, ManagerEntity manager) {
        manager.setId(managerId);

        return managerRepository.findById(managerId).map(existingManager -> {

            Optional.ofNullable(manager.getFirstName()).ifPresent(existingManager::setFirstName);
            Optional.ofNullable(manager.getLastName()).ifPresent(existingManager::setLastName);
            Optional.ofNullable(manager.getDepartment()).ifPresent(existingManager::setDepartment);
            Optional.ofNullable(manager.getAge()).ifPresent(existingManager::setAge);

            return managerRepository.save(existingManager);

        }).orElseThrow(() -> new EntityNotFoundException(ManagerEntity.class, managerId));
    }

    @Override
    public void deleteManager(long managerId) {
        Optional<ManagerEntity> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isPresent()) {
            managerRepository.deleteById(managerId);
        } else {
            throw new EntityNotFoundException(ManagerEntity.class, managerId);
        }
    }

    @Override
    public void deleteAllManagersOfOffice(long officeId) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(officeId);
        if (optionalOffice.isPresent()) {
            managerRepository.deleteByOfficeId(officeId);
        } else {
            throw new EntityNotFoundException(OfficeEntity.class, officeId);
        }
    }

    @Override
    public boolean isExists(long officeId) {
        return managerRepository.existsById(officeId);
    }

    private static OfficeEntity unwrapOffice(Optional<OfficeEntity> entity, long officeId) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(OfficeEntity.class, officeId);
    }
}
