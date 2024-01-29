package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.ClerkEntity;

import java.util.List;
import java.util.Optional;

public interface ClerkService {

    ClerkEntity createUpdateClerk(long id, ClerkEntity clerk);

    List<ClerkEntity> findAll();

    Optional<ClerkEntity> findClerkById(long id);

    boolean isExists(long id);

    ClerkEntity partialUpdate(long id, ClerkEntity clerk);

    void deleteById(long id);

}
