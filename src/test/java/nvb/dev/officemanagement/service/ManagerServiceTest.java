package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.service.impl.ManagerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidManager;
import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ManagerServiceTest {

    @Mock
    ManagerRepository managerRepository;

    @InjectMocks
    ManagerServiceImpl managerService;

    @Test
    @DisplayName("test that create manager saves manager")
    void testThatCreateManagerSavesManager() {
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidManager());

        ManagerEntity savedManager = managerService.createManager(anyValidManager());

        assertEquals("dummy", savedManager.getFirstName());
        assertEquals("dummy", savedManager.getLastName());

        verify(managerRepository, atLeastOnce()).save(any(ManagerEntity.class));
    }

    @Test
    @DisplayName("test that update manager updates the existing manager")
    void testThatUpdateManagerUpdatesTheExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidUpdatedManager());

        ManagerEntity result = managerService.updateManager(1L, anyValidUpdatedManager());

        assertEquals(anyValidUpdatedManager().getFirstName(), result.getFirstName());
        assertEquals(anyValidUpdatedManager().getLastName(), result.getLastName());

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).save(any(ManagerEntity.class));
    }

    @Test
    @DisplayName("test that update manager cannot update the non existing manager")
    void testThatUpdateManagerCannotUpdateTheNonExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.updateManager(1L, anyValidManager()));
        verify(managerRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find all returns list of managers")
    void testThatFindAllReturnsListOfManagers() {
        List<ManagerEntity> managerEntityList = List.of(anyValidManager(), anyValidManager());

        when(managerRepository.findAll()).thenReturn(managerEntityList);

        List<ManagerEntity> result = managerService.findAll();

        assertEquals(2, result.size());
        verify(managerRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find all returns empty list")
    void testThatFindAllReturnsEmptyList() {
        when(managerRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> managerService.findAll());
        verify(managerRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find manager by id returns manager")
    void testThatFindManagerByIdReturnsManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));

        Optional<ManagerEntity> optionalManager = managerService.findManagerById(1L);

        assertEquals("dummy", optionalManager.get().getFirstName());
        assertEquals("dummy", optionalManager.get().getLastName());

        verify(managerRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find manager by id returns nothing")
    void testThatFindManagerByIdReturnsNothing() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.findManagerById(1L));
        verify(managerRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns true")
    void testThatIsExistsReturnsTrue() {
        when(managerRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = managerService.isExists(1L);

        assertTrue(exists);
        verify(managerRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns false")
    void testThatIsExistsReturnsFalse() {
        when(managerRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = managerService.isExists(1L);

        assertFalse(exists);
        verify(managerRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that partial update updates the existing manager")
    void testThatPartialUpdateUpdatesTheExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidUpdatedManager());

        ManagerEntity result = managerService.partialUpdate(1L, anyValidUpdatedManager());

        assertEquals(anyValidUpdatedManager().getFirstName(), result.getFirstName());
        assertEquals(anyValidUpdatedManager().getLastName(), result.getLastName());

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).save(any(ManagerEntity.class));
    }

    @Test
    @DisplayName("test that partial update cannot update the non existing manager")
    void testThatPartialUpdateCannotUpdateTheNonExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                managerService.partialUpdate(1L, anyValidManager()));

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).save(any(ManagerEntity.class));
    }

    @Test
    @DisplayName("test that delete by id deletes the existing manager")
    void testThatDeleteByIdDeletesTheExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));

        managerService.deleteById(1L);

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    @DisplayName("test that delete by id cannot delete the non existing manager")
    void testThatDeleteByIdCannotDeleteTheNonExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.deleteById(1L));

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).deleteById(anyLong());
    }

}