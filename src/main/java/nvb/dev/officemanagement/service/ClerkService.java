package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.ClerkEntity;

import java.util.List;
import java.util.Optional;

public interface ClerkService {

    ClerkEntity createClerk(long officeId, long managerId, ClerkEntity clerk);

    List<ClerkEntity> getAllClerksByOfficeIdAndManagerId(long officeId, long managerId);

    Optional<ClerkEntity> getClerkById(long clerkId);

    ClerkEntity updateClerk(long clerkId, ClerkEntity clerk);

    ClerkEntity partialUpdate(long clerkId, ClerkEntity clerk);

    void deleteClerk(long clerkId);

    void deleteAllClerksOfManager(long managerId);

    boolean isExists(long clerkId);

}
