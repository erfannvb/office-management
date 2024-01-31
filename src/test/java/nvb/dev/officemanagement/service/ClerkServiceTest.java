package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.repository.ClerkRepository;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.impl.ClerkServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
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
    void testThatCreateClerksSavesClerk() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());

        ClerkEntity savedClerk = clerkService.createClerk(1L, 1L, anyValidClerk());

        assertEquals("dummy", savedClerk.getFirstName());
        assertEquals("dummy", savedClerk.getLastName());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatCreateClerkCannotSaveClerkWhenOfficeDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());

        assertThrows(EntityNotFoundException.class, () ->
                clerkService.createClerk(1L, 1L, anyValidClerk()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatCreateClerkCannotSaveClerkWhenManagerDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());

        assertThrows(EntityNotFoundException.class, () ->
                clerkService.createClerk(1L, 1L, anyValidClerk()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatCreateClerkCannotSaveClerkWhenOfficeAndManagerDoNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidClerk());

        assertThrows(EntityNotFoundException.class, () ->
                clerkService.createClerk(1L, 1L, anyValidClerk()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatGetAllClerksByOfficeIdAndManagerIdReturnsListOfClerks() {
        when(clerkRepository.findByOfficeIdAndManagerId(anyLong(), anyLong()))
                .thenReturn(List.of(anyValidClerk(), anyValidClerk()));

        List<ClerkEntity> clerks = clerkService.getAllClerksByOfficeIdAndManagerId(1L, 1L);

        assertEquals("dummy", clerks.getFirst().getFirstName());
        assertEquals("dummy", clerks.getFirst().getDepartment());
        assertEquals("dummy", clerks.getLast().getFirstName());
        assertEquals("dummy", clerks.getLast().getDepartment());

        verify(clerkRepository, atLeastOnce()).findByOfficeIdAndManagerId(anyLong(), anyLong());
    }

    @Test
    void testThatGetClerkByIdReturnsClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));

        Optional<ClerkEntity> clerk = clerkService.getClerkById(1L);

        assertEquals("dummy", clerk.get().getFirstName());
        assertEquals("dummy", clerk.get().getDepartment());

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatUpdateClerkUpdatesTheExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());

        ClerkEntity updatedClerk = clerkService.updateClerk(1L, anyValidUpdatedClerk());

        assertEquals("updatedDummy", updatedClerk.getFirstName());
        assertEquals("updatedDummy", updatedClerk.getLastName());

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatUpdateClerkCannotUpdateWhenClerkDoesNotExist() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());

        assertThrows(EntityNotFoundException.class, () ->
                clerkService.updateClerk(1L, anyValidUpdatedClerk()));

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatPartialUpdateUpdatesTheExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());

        ClerkEntity clerkEntity = clerkService.partialUpdate(1L, anyValidUpdatedClerk());

        assertEquals("updatedDummy", clerkEntity.getFirstName());

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatPartialUpdateCannotUpdateWhenClerkDoesNotExist() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(clerkRepository.save(any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());

        assertThrows(EntityNotFoundException.class, () ->
                clerkService.partialUpdate(1L, anyValidUpdatedClerk()));

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).save(any(ClerkEntity.class));
    }

    @Test
    void testThatDeleteClerkDeletesTheExistingClerk() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.of(anyValidClerk()));

        clerkService.deleteClerk(1L);

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteClerkCannotDeleteWhenClerkDoesNotExist() {
        when(clerkRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.deleteClerk(1L));

        verify(clerkRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteAllClerksOfManagerDeletesTheExistingClerks() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));

        clerkService.deleteAllClerksOfManager(1L);

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, atLeastOnce()).deleteByManagerId(anyLong());
    }

    @Test
    void testThatDeleteAllClerksOfManagerCannotDeleteClerksOfNonExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clerkService.deleteAllClerksOfManager(1L));

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(clerkRepository, never()).deleteByManagerId(anyLong());
    }

    @Test
    void testThatIsExistsReturnsTrue() {
        when(clerkRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = clerkService.isExists(1L);

        assertTrue(exists);
        verify(clerkRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    void testThatIsExistsReturnsFalse() {
        when(clerkRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = clerkService.isExists(1L);

        assertFalse(exists);
        verify(clerkRepository, atLeastOnce()).existsById(anyLong());
    }

}