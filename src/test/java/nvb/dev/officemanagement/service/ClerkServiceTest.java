package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.ClerkRepository;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.impl.ClerkServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ClerkServiceTest {

    @Mock
    ClerkRepository clerkRepository;

    @Mock
    OfficeRepository officeRepository;

    @Mock
    ManagerRepository managerRepository;

    @InjectMocks
    ClerkServiceImpl clerkService;

    @Test
    @DisplayName("test that create clerk saves clerk")
    void testThatCreateClerkSavesClerk() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());

        ClerkEntity savedClerk = clerkService.createClerk(anyValidClerk(), anyValidOffice().getId(),
                anyValidManager().getId());

        assertEquals("dummy", savedClerk.getFirstName());
        assertEquals("dummy", savedClerk.getLastName());

        verify(clerkRepository, atLeastOnce()).save(any(ClerkEntity.class));
    }

    @Test
    @DisplayName("test that update clerk updates the existing clerk")
    void testThatUpdateClerkUpdatesTheExistingClerk() {
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());

        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));

        ClerkEntity updatedClerk = clerkService.updateClerk(anyValidUpdatedClerk(),
                anyValidOffice().getId(), anyValidManager().getId());

        assertEquals(anyValidUpdatedClerk().getFirstName(), updatedClerk.getFirstName());
        assertEquals(anyValidUpdatedClerk().getLastName(), updatedClerk.getLastName());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).save(any(ClerkEntity.class));
    }

    @Test
    @DisplayName("test that update clerk cannot update when office does not exist")
    void testThatUpdateClerkCannotUpdateWhenOfficeDoesNotExist() {
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.updateClerk(anyValidClerk(),
                anyValidOffice().getId(), anyValidManager().getId()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    @DisplayName("test that update clerk cannot update when manager does not exist")
    void testThatUpdateClerkCannotUpdateWhenManagerDoesNotExist() {
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.updateClerk(anyValidClerk(),
                anyValidOffice().getId(), anyValidManager().getId()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    @DisplayName("test that update clerk cannot update when office and manager don't exist")
    void testThatUpdateClerkCannotUpdateWhenOfficeAndManagerDontExist() {
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.updateClerk(anyValidClerk(),
                anyValidOffice().getId(), anyValidManager().getId()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    @DisplayName("test that find all returns list of clerks")
    void testThatFindAllReturnsListOfClerks() {
        List<ClerkEntity> clerkEntityList = List.of(anyValidClerk(), anyValidClerk());

        when(clerkRepository.findAll()).thenReturn(clerkEntityList);

        List<ClerkEntity> result = clerkService.findAll();

        assertEquals(2, result.size());
        verify(clerkRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find all returns empty list")
    void testThatFindAllReturnsEmptyList() {
        when(clerkRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> clerkService.findAll());
        verify(clerkRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find clerk by id returns clerk")
    void testThatFindClerkByIdReturnsClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));

        Optional<ClerkEntity> clerk = clerkService.findClerkById(1L);

        assertEquals("dummy", clerk.get().getFirstName());
        assertEquals("dummy", clerk.get().getLastName());

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find clerk by id returns nothing")
    void testThatFindClerkByIdReturnsNothing() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.findClerkById(1L));
        verify(clerkRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find clerk by office id and manager id returns clerk")
    void testThatFindClerkByOfficeIdAndManagerIdReturnsClerk() {
        when(clerkRepository.findByOfficeIdAndManagerId(anyLong(), anyLong())).thenReturn(Optional.of(anyValidClerk()));

        ClerkEntity result = clerkService
                .findClerkByOfficeIdAndManagerId(anyValidOffice().getId(), anyValidManager().getId());

        assertEquals("dummy", result.getFirstName());
        assertEquals("dummy", result.getLastName());

        verify(clerkRepository, atLeastOnce()).findByOfficeIdAndManagerId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("test that find clerk by office id and manager id returns nothing")
    void testThatFindClerkByOfficeIdAndManagerIdReturnsNothing() {
        when(clerkRepository.findByOfficeIdAndManagerId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () ->
                clerkService.findClerkByOfficeIdAndManagerId(anyValidOffice().getId(),
                        anyValidManager().getId()));

        verify(clerkRepository, atLeastOnce()).findByOfficeIdAndManagerId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("test that is exists returns true")
    void testThatIsExistsReturnsTrue() {
        when(clerkRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = clerkService.isExists(1L);

        assertTrue(exists);
        verify(clerkRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns false")
    void testThatIsExistsReturnsFalse() {
        when(clerkRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = clerkService.isExists(1L);

        assertFalse(exists);
        verify(clerkRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that partial update updates the existing clerk")
    void testThatPartialUpdateUpdatesTheExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());

        ClerkEntity updatedClerk = clerkService.partialUpdate(1L, anyValidUpdatedClerk());

        assertEquals(anyValidUpdatedClerk().getFirstName(), updatedClerk.getFirstName());
        assertEquals(anyValidUpdatedClerk().getLastName(), updatedClerk.getLastName());

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).save(any(ClerkEntity.class));
    }

    @Test
    @DisplayName("test that partial update cannot update the non existing clerk")
    void testThatPartialUpdateCannotUpdateTheNonExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.partialUpdate(1L, anyValidClerk()));
        verify(clerkRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that delete by id deletes the existing clerk")
    void testThatDeleteByIdDeletesTheExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));

        clerkService.deleteById(1L);

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    @DisplayName("test that delete by id cannot delete the non existing clerk")
    void testThatDeleteByIdCannotDeleteTheNonExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.deleteById(1L));

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).deleteById(anyLong());
    }

}