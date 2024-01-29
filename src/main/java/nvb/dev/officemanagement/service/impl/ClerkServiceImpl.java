package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
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
    public ClerkEntity createClerk(ClerkEntity clerk) {
        return clerkRepository.save(clerk);
    }

    @Override
    public ClerkEntity updateClerk(long id, ClerkEntity clerk) {
        Optional<ClerkEntity> optionalClerk = clerkRepository.findById(id);
        if (optionalClerk.isPresent()) {

            ClerkEntity currentClerk = optionalClerk.get();

            currentClerk.setFirstName(clerk.getFirstName());
            currentClerk.setLastName(clerk.getLastName());
            currentClerk.setDepartment(clerk.getDepartment());
            currentClerk.setAge(clerk.getAge());

            return clerkRepository.save(currentClerk);

        } else {
            throw new EntityNotFoundException(ClerkEntity.class, id);
        }
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
        clerkRepository.deleteById(id);
    }
}
