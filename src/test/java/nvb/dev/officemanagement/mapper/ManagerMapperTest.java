package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.ManagerDto;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.officemanagement.MotherObject.anyValidManager;
import static nvb.dev.officemanagement.MotherObject.anyValidManagerDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ManagerMapperImpl.class})
class ManagerMapperTest {

    @Autowired
    ManagerMapper managerMapper;

    @Test
    void toManagerDto() {
        ManagerDto managerDto = managerMapper.toManagerDto(anyValidManager());

        assertEquals(anyValidManager().getId(), managerDto.getId());
        assertEquals(anyValidManager().getFirstName(), managerDto.getFirstName());
        assertEquals(anyValidManager().getLastName(), managerDto.getLastName());
        assertEquals(anyValidManager().getDepartment(), managerDto.getDepartment());
        assertEquals(anyValidManager().getAge(), managerDto.getAge());
        assertEquals(anyValidManager().getOffice(), managerDto.getOffice());
        assertEquals(anyValidManager().getClerks(), managerDto.getClerks());
        assertEquals(anyValidManager().getDocuments(), managerDto.getDocuments());
    }

    @Test
    void toManagerDto_IsNull() {
        ManagerDto managerDto = managerMapper.toManagerDto(null);
        assertNull(managerDto);
    }

    @Test
    void toManagerEntity() {
        ManagerEntity managerEntity = managerMapper.toManagerEntity(anyValidManagerDto());

        assertEquals(anyValidManagerDto().getId(), managerEntity.getId());
        assertEquals(anyValidManagerDto().getFirstName(), managerEntity.getFirstName());
        assertEquals(anyValidManagerDto().getLastName(), managerEntity.getLastName());
        assertEquals(anyValidManagerDto().getDepartment(), managerEntity.getDepartment());
        assertEquals(anyValidManagerDto().getAge(), managerEntity.getAge());
        assertEquals(anyValidManagerDto().getOffice(), managerEntity.getOffice());
        assertEquals(anyValidManagerDto().getClerks(), managerEntity.getClerks());
        assertEquals(anyValidManagerDto().getDocuments(), managerEntity.getDocuments());
    }

    @Test
    void toManagerEntity_IsNull() {
        ManagerEntity managerEntity = managerMapper.toManagerEntity(null);
        assertNull(managerEntity);
    }
}