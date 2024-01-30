package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.impl.OfficeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidOffice;
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
    void testThatCreateOfficeSaveOffice() {
        when(officeRepository.save(any(OfficeEntity.class))).thenReturn(anyValidOffice());

        OfficeEntity officeEntity = officeService.createOffice(anyValidOffice());

        assertEquals("dummy", officeEntity.getOfficeName());
        assertEquals("dummy", officeEntity.getOfficeCode());

        verify(officeRepository, atLeastOnce()).save(any(OfficeEntity.class));
    }

    @Test
    void testThatGetAllOfficesReturnsListOfOffices() {
        when(officeRepository.findAll()).thenReturn(List.of(anyValidOffice(), anyValidOffice()));

        List<OfficeEntity> allOffices = officeService.getAllOffices();

        assertEquals(2, allOffices.size());
        verify(officeRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAllOfficesReturnsEmptyList() {
        when(officeRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> officeService.getAllOffices());
        verify(officeRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetOfficeByIdReturnsAnOffice() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));

        Optional<OfficeEntity> officeEntity = officeService.getOfficeById(1L);

        assertEquals("dummy", officeEntity.get().getOfficeName());
        assertEquals("dummy", officeEntity.get().getOfficeCode());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetOfficeByIdReturnsNothing() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> officeService.getOfficeById(1L));
        verify(officeRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetOfficeByOfficeNameReturnsAnOffice() {
        when(officeRepository.findByOfficeName(anyString())).thenReturn(Optional.of(anyValidOffice()));

        Optional<OfficeEntity> optionalOffice = officeService.getOfficeByOfficeName("dummy");

        assertEquals("dummy", optionalOffice.get().getOfficeName());
        assertEquals("dummy", optionalOffice.get().getOfficeCode());

        verify(officeRepository, atLeastOnce()).findByOfficeName(anyString());
    }

    @Test
    void testThatGetOfficeByOfficeNameReturnsNothing() {
        when(officeRepository.findByOfficeName(anyString())).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> officeService.getOfficeByOfficeName(""));
        verify(officeRepository, atLeastOnce()).findByOfficeName(anyString());
    }

}