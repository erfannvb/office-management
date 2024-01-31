package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.ClerkRepository;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.ClerkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClerkServiceImpl implements ClerkService {

    private final ClerkRepository clerkRepository;
    private final OfficeRepository officeRepository;
    private final ManagerRepository managerRepository;

    @Override
    public ClerkEntity createClerk(long officeId, long managerId, ClerkEntity clerk) {
        OfficeEntity officeEntity = unwrapOffice(officeRepository.findById(officeId), officeId);
        ManagerEntity managerEntity = unwrapManager(managerRepository.findById(managerId), managerId);
        clerk.setOffice(officeEntity);
        clerk.setManager(managerEntity);
        return clerkRepository.save(clerk);
    }

    @Override
    public List<ClerkEntity> getAllClerksByOfficeIdAndManagerId(long officeId, long managerId) {
        return clerkRepository.findByOfficeIdAndManagerId(officeId, managerId);
    }

    @Override
    public Optional<ClerkEntity> getClerkById(long clerkId) {
        return clerkRepository.findById(clerkId);
    }

    @Override
    public ClerkEntity updateClerk(long clerkId, ClerkEntity clerk) {
        Optional<ClerkEntity> optionalClerk = clerkRepository.findById(clerkId);
        if (optionalClerk.isPresent()) {

            ClerkEntity currentClerk = optionalClerk.get();

            currentClerk.setFirstName(clerk.getFirstName());
            currentClerk.setLastName(clerk.getLastName());
            currentClerk.setDepartment(clerk.getDepartment());
            currentClerk.setAge(clerk.getAge());

            return clerkRepository.save(currentClerk);

        } else {
            throw new EntityNotFoundException(ClerkEntity.class, clerkId);
        }
    }

    @Override
    public ClerkEntity partialUpdate(long clerkId, ClerkEntity clerk) {
        clerk.setId(clerkId);

        return clerkRepository.findById(clerkId).map(existingClerk -> {

            Optional.ofNullable(clerk.getFirstName()).ifPresent(existingClerk::setFirstName);
            Optional.ofNullable(clerk.getLastName()).ifPresent(existingClerk::setLastName);
            Optional.ofNullable(clerk.getDepartment()).ifPresent(existingClerk::setDepartment);
            Optional.ofNullable(clerk.getAge()).ifPresent(existingClerk::setAge);

            return clerkRepository.save(existingClerk);

        }).orElseThrow(() -> new EntityNotFoundException(ClerkEntity.class, clerkId));
    }

    @Override
    public void deleteClerk(long clerkId) {
        Optional<ClerkEntity> optionalClerk = clerkRepository.findById(clerkId);
        if (optionalClerk.isPresent()) {
            clerkRepository.deleteById(clerkId);
        } else {
            throw new EntityNotFoundException(ClerkEntity.class, clerkId);
        }
    }

    @Override
    public void deleteAllClerksOfManager(long managerId) {
        Optional<ManagerEntity> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isPresent()) {
            clerkRepository.deleteByManagerId(managerId);
        } else {
            throw new EntityNotFoundException(ManagerEntity.class, managerId);
        }
    }

    @Override
    public boolean isExists(long clerkId) {
        return clerkRepository.existsById(clerkId);
    }

    private static OfficeEntity unwrapOffice(Optional<OfficeEntity> entity, long officeId) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(OfficeEntity.class, officeId);
    }

    private static ManagerEntity unwrapManager(Optional<ManagerEntity> entity, long officeId) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(ManagerEntity.class, officeId);
    }

}
