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
    public ClerkEntity createClerk(ClerkEntity clerk, long officeId, long managerId) {
        OfficeEntity officeEntity = unwrapOffice(officeRepository.findById(officeId), officeId);
        ManagerEntity managerEntity = unwrapManager(managerRepository.findById(managerId), managerId);

        clerk.setOffice(officeEntity);
        clerk.setManager(managerEntity);

        return clerkRepository.save(clerk);
    }

    @Override
    public ClerkEntity updateClerk(ClerkEntity clerk, long officeId, long managerId) {
        OfficeEntity officeEntity = unwrapOffice(officeRepository.findById(officeId), officeId);
        ManagerEntity managerEntity = unwrapManager(managerRepository.findById(managerId), managerId);

        clerk.setFirstName(clerk.getFirstName());
        clerk.setLastName(clerk.getLastName());
        clerk.setDepartment(clerk.getDepartment());
        clerk.setAge(clerk.getAge());

        clerk.setOffice(officeEntity);
        clerk.setManager(managerEntity);

        return clerkRepository.save(clerk);
    }

    @Override
    public List<ClerkEntity> findAll() {
        List<ClerkEntity> clerkEntityList = clerkRepository.findAll();
        if (clerkEntityList.isEmpty()) throw new NoDataFoundException();
        return clerkEntityList;
    }

    @Override
    public Optional<ClerkEntity> findClerkById(long id) {
        return Optional.ofNullable(clerkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ClerkEntity.class, id)));
    }

    @Override
    public ClerkEntity findClerkByOfficeIdAndManagerId(long officeId, long managerId) {
        Optional<ClerkEntity> clerkEntity =
                clerkRepository.findByOfficeIdAndManagerId(officeId, managerId);
        return unwrapClerk(clerkEntity, clerkEntity.get().getId());
    }

    @Override
    public boolean isExists(long id) {
        return clerkRepository.existsById(id);
    }

    @Override
    public ClerkEntity partialUpdate(long id, ClerkEntity clerk) {
        clerk.setId(id);

        return clerkRepository.findById(id).map(existingClerk -> {

            Optional.ofNullable(clerk.getFirstName()).ifPresent(existingClerk::setFirstName);
            Optional.ofNullable(clerk.getLastName()).ifPresent(existingClerk::setLastName);
            Optional.ofNullable(clerk.getDepartment()).ifPresent(existingClerk::setDepartment);
            Optional.ofNullable(clerk.getAge()).ifPresent(existingClerk::setAge);

            return clerkRepository.save(existingClerk);

        }).orElseThrow(() -> new EntityNotFoundException(ClerkEntity.class, id));
    }

    @Override
    public void deleteById(long id) {
        Optional<ClerkEntity> optionalClerk = clerkRepository.findById(id);
        if (optionalClerk.isPresent()) {
            clerkRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(ClerkEntity.class, id);
        }
    }

    private static ClerkEntity unwrapClerk(Optional<ClerkEntity> entity, long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(ClerkEntity.class, id);
    }

    private static OfficeEntity unwrapOffice(Optional<OfficeEntity> entity, long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(OfficeEntity.class, id);
    }

    private static ManagerEntity unwrapManager(Optional<ManagerEntity> entity, long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(ManagerEntity.class, id);
    }
}
