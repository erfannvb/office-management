package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.OfficeDto;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.officemanagement.MotherObject.anyValidOffice;
import static nvb.dev.officemanagement.MotherObject.anyValidOfficeDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OfficeMapperImpl.class})
class OfficeMapperTest {

    @Autowired
    OfficeMapper officeMapper;

    @Test
    void toOfficeDto() {
        OfficeDto officeDto = officeMapper.toOfficeDto(anyValidOffice());

        assertEquals(anyValidOffice().getId(), officeDto.getId());
        assertEquals(anyValidOffice().getOfficeName(), officeDto.getOfficeName());
        assertEquals(anyValidOffice().getOfficeCode(), officeDto.getOfficeCode());
        assertEquals(anyValidOffice().getOfficePhoneNumber(), officeDto.getOfficePhoneNumber());
        assertEquals(anyValidOffice().getAddress(), officeDto.getAddress());
    }

    @Test
    void toOfficeDto_IsNull() {
        OfficeDto officeDto = officeMapper.toOfficeDto(null);
        assertNull(officeDto);
    }

    @Test
    void toOfficeEntity() {
        OfficeEntity officeEntity = officeMapper.toOfficeEntity(anyValidOfficeDto());

        assertEquals(anyValidOfficeDto().getId(), officeEntity.getId());
        assertEquals(anyValidOfficeDto().getOfficeName(), officeEntity.getOfficeName());
        assertEquals(anyValidOfficeDto().getOfficeCode(), officeEntity.getOfficeCode());
        assertEquals(anyValidOfficeDto().getOfficePhoneNumber(), officeEntity.getOfficePhoneNumber());
        assertEquals(anyValidOfficeDto().getAddress(), officeEntity.getAddress());
    }

    @Test
    void toOfficeEntity_IsNull() {
        OfficeEntity officeEntity = officeMapper.toOfficeEntity(null);
        assertNull(officeEntity);
    }
}