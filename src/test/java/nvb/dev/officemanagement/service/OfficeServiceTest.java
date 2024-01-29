package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.impl.OfficeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidOffice;
import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedOffice;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OfficeServiceTest {

    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    OfficeServiceImpl officeService;

    @Test
    @DisplayName("test that create office saves office")
    void testThatCreateOfficeSavesOffice() {
        when(officeRepository.save(any(OfficeEntity.class))).thenReturn(anyValidOffice());

        OfficeEntity savedOffice = officeService.createOffice(anyValidOffice());

        assertEquals("dummy", savedOffice.getOfficeName());
        assertEquals("dummy", savedOffice.getOfficeCode());

        verify(officeRepository, atLeastOnce()).save(any(OfficeEntity.class));
    }

    @Test
    @DisplayName("test that update office updates the existing office")
    void testThatUpdateOfficeUpdatesTheExistingOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(officeRepository.save(any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());

        OfficeEntity result = officeService.updateOffice(1L, anyValidUpdatedOffice());

        assertEquals(anyValidUpdatedOffice().getOfficeName(), result.getOfficeName());
        assertEquals(anyValidUpdatedOffice().getOfficeCode(), result.getOfficeCode());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(officeRepository, atLeastOnce()).save(any(OfficeEntity.class));
    }

    @Test
    @DisplayName("test that update office cannot update the non existing office")
    void testThatUpdateOfficeCannotUpdateTheNonExistingOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                officeService.updateOffice(1L, anyValidOffice()));
        verify(officeRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find all returns list of offices")
    void testThatFindAllReturnsListOfOffices() {
        List<OfficeEntity> officeEntityList = List.of(anyValidOffice(), anyValidOffice());

        when(officeRepository.findAll()).thenReturn(officeEntityList);

        List<OfficeEntity> result = officeService.findAll();

        assertEquals(2, result.size());
        verify(officeRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find all returns empty list")
    void testThatFindAllReturnsEmptyList() {
        when(officeRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> officeService.findAll());
        verify(officeRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find office by id returns office")
    void testThatFindOfficeByIdReturnsOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));

        Optional<OfficeEntity> result = officeService.findOfficeById(1L);

        assertEquals("dummy", result.get().getOfficeName());
        assertEquals("dummy", result.get().getOfficeCode());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find office by id returns nothing")
    void testThatFindOfficeByIdReturnsNothing() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> officeService.findOfficeById(1L));
        verify(officeRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns true")
    void testThatIsExistsReturnsTrue() {
        when(officeRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = officeService.isExists(1L);

        assertTrue(exists);
        verify(officeRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns false")
    void testThatIsExistsReturnsFalse() {
        when(officeRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = officeService.isExists(1L);

        assertFalse(exists);
        verify(officeRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that partial update updates the existing office")
    void testThatPartialUpdateUpdatesTheExistingOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(officeRepository.save(any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());

        OfficeEntity updatedOffice = officeService.partialUpdate(1L, anyValidUpdatedOffice());

        assertEquals(anyValidUpdatedOffice().getOfficeName(), updatedOffice.getOfficeName());
        assertEquals(anyValidUpdatedOffice().getOfficeCode(), updatedOffice.getOfficeCode());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(officeRepository, atLeastOnce()).save(any(OfficeEntity.class));
    }

    @Test
    @DisplayName("test that partial update cannot update the non existing office")
    void testThatPartialUpdateCannotUpdateTheNonExistingOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> officeService.partialUpdate(1L, anyValidUpdatedOffice()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(officeRepository, never()).save(any(OfficeEntity.class));
    }

    @Test
    @DisplayName("test that delete by id deletes the existing office")
    void testThatDeleteByIdDeletesTheExistingOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));

        officeService.deleteById(1L);

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(officeRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    @DisplayName("test that delete by id cannot delete the non existing office")
    void testThatDeleteByIdCannotDeleteTheNonExistingOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> officeService.deleteById(1L));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(officeRepository, never()).deleteById(anyLong());
    }

}