package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.repository.ManagerRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.impl.ManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ManagerServiceTest {

    @Mock
    ManagerRepository managerRepository;

    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    ManagerServiceImpl managerService;

    @Test
    void testThatCreateManagerSavesManager() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidManager());

        ManagerEntity savedManager = managerService.createManager(1L, anyValidManager());

        assertEquals(1, savedManager.getOffice().getId());
        assertEquals("dummy", savedManager.getFirstName());
        assertEquals("dummy", savedManager.getOffice().getOfficeName());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).save(any(ManagerEntity.class));
    }

    @Test
    void testThatCreateManagerCannotSaveManagerWhenOfficeDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidManager());

        assertThrows(EntityNotFoundException.class,
                () -> managerService.createManager(1L, anyValidManager()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).save(any(ManagerEntity.class));
    }

    @Test
    void testThatGetManagerByOfficeIdReturnsAnExistingManager() {
        when(managerRepository.findByOfficeId(anyLong())).thenReturn(Optional.of(anyValidManager()));

        ManagerEntity manager = managerService.getManagerByOfficeId(1L);

        assertEquals("dummy", manager.getFirstName());
        assertEquals("dummy", manager.getLastName());
        assertEquals("dummy", manager.getOffice().getOfficeName());

        verify(managerRepository, atLeastOnce()).findByOfficeId(anyLong());
    }

    @Test
    void testThatGetManagerByOfficeIdReturnsNothingWhenOfficeDoesNotExist() {
        when(managerRepository.findByOfficeId(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.getManagerByOfficeId(1L));
        verify(managerRepository, atLeastOnce()).findByOfficeId(anyLong());
    }

    @Test
    void testThatGetAllManagersByOfficeIdReturnsExistingManagers() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(managerRepository.findAllByOfficeId(anyLong()))
                .thenReturn(List.of(anyValidManager(), anyValidManager()));

        List<ManagerEntity> result = managerService.getAllManagersByOfficeId(1L);

        assertEquals("dummy", result.getFirst().getFirstName());
        assertEquals("dummy", result.getFirst().getLastName());
        assertEquals("dummy", result.getFirst().getOffice().getOfficeName());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).findAllByOfficeId(anyLong());
    }

    @Test
    void testThatGetAllManagersByOfficeIdReturnsNothingWhenOfficeDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.findAllByOfficeId(anyLong())).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> managerService.getAllManagersByOfficeId(1L));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).findAllByOfficeId(anyLong());
    }

    @Test
    void testThatUpdateManagerUpdatesTheExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidUpdatedManager());

        ManagerEntity updatedManager = managerService.updateManager(1L, anyValidUpdatedManager());

        assertEquals("updatedDummy", updatedManager.getFirstName());
        assertEquals("updatedDummy", updatedManager.getDepartment());
        assertEquals("updatedDummy", updatedManager.getOffice().getOfficeName());

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).save(any(ManagerEntity.class));
    }

    @Test
    void testThatUpdateManagerCannotUpdateWhenManagerDoesNotExist() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidManager());

        assertThrows(EntityNotFoundException.class, () ->
                managerService.updateManager(1L, anyValidUpdatedManager()));

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).save(any(ManagerEntity.class));
    }

    @Test
    void testThatPartialUpdateUpdatesTheExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidManager());

        ManagerEntity partialUpdate = managerService.partialUpdate(1L, anyValidManager());

        assertEquals("dummy", partialUpdate.getFirstName());
        assertEquals("dummy", partialUpdate.getOffice().getOfficeCode());

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).save(any(ManagerEntity.class));
    }

    @Test
    void testThatPartialUpdateCannotUpdateWhenManagerDoesNotExist() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(managerRepository.save(any(ManagerEntity.class))).thenReturn(anyValidManager());

        assertThrows(EntityNotFoundException.class, () ->
                managerService.partialUpdate(1L, anyValidManager()));

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).save(any(ManagerEntity.class));
    }

    @Test
    void testThatDeleteManagerDeletesTheExistingManager() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(anyValidManager()));

        managerService.deleteManager(1L);

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteManagerCannotDeleteWhenManagerDoesNotExist() {
        when(managerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.deleteManager(1L));

        verify(managerRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteAllManagersOfOfficeDeletesTheExistingManagers() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));

        managerService.deleteAllManagersOfOffice(1L);

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, atLeastOnce()).deleteByOfficeId(anyLong());
    }

    @Test
    void testThatDeleteAllManagersOfOfficeCannotDeleteWhenOfficeDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.deleteAllManagersOfOffice(1L));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(managerRepository, never()).deleteByOfficeId(anyLong());
    }

}