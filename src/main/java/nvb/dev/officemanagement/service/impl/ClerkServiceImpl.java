package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.repository.ClerkRepository;
import nvb.dev.officemanagement.service.ClerkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClerkServiceImpl implements ClerkService {

    private final ClerkRepository clerkRepository;

    @Override
    public ClerkEntity createUpdateClerk(long id, ClerkEntity clerk) {
        clerk.setId(id);
        return clerkRepository.save(clerk);
    }

    @Override
    public List<ClerkEntity> findAll() {
        return clerkRepository.findAll();
    }

    @Override
    public Optional<ClerkEntity> findClerkById(long id) {
        return clerkRepository.findById(id);
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

        }).orElseThrow();
    }

    @Override
    public void deleteById(long id) {
        clerkRepository.deleteById(id);
    }
}
