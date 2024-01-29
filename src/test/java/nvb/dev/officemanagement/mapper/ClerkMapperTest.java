package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.ClerkDto;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.officemanagement.MotherObject.anyValidClerk;
import static nvb.dev.officemanagement.MotherObject.anyValidClerkDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ClerkMapperImpl.class})
class ClerkMapperTest {

    @Autowired
    ClerkMapper clerkMapper;

    @Test
    void toClerkDto() {
        ClerkDto clerkDto = clerkMapper.toClerkDto(anyValidClerk());

        assertEquals(anyValidClerk().getId(), clerkDto.getId());
        assertEquals(anyValidClerk().getFirstName(), clerkDto.getFirstName());
        assertEquals(anyValidClerk().getLastName(), clerkDto.getLastName());
        assertEquals(anyValidClerk().getDepartment(), clerkDto.getDepartment());
        assertEquals(anyValidClerk().getAge(), clerkDto.getAge());
        assertEquals(anyValidClerk().getOffice(), clerkDto.getOffice());
        assertEquals(anyValidClerk().getManager(), clerkDto.getManager());
        assertEquals(anyValidClerk().getDocuments(), clerkDto.getDocuments());
    }

    @Test
    void toClerkDto_IsNull() {
        ClerkDto clerkDto = clerkMapper.toClerkDto(null);
        assertNull(clerkDto);
    }

    @Test
    void toClerkEntity() {
        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(anyValidClerkDto());

        assertEquals(anyValidClerkDto().getId(), clerkEntity.getId());
        assertEquals(anyValidClerkDto().getFirstName(), clerkEntity.getFirstName());
        assertEquals(anyValidClerkDto().getLastName(), clerkEntity.getLastName());
        assertEquals(anyValidClerkDto().getDepartment(), clerkEntity.getDepartment());
        assertEquals(anyValidClerkDto().getAge(), clerkEntity.getAge());
        assertEquals(anyValidClerkDto().getOffice(), clerkEntity.getOffice());
        assertEquals(anyValidClerkDto().getManager(), clerkEntity.getManager());
        assertEquals(anyValidClerkDto().getDocuments(), clerkEntity.getDocuments());
    }

    @Test
    void toClerkEntity_IsNull() {
        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(null);
        assertNull(clerkEntity);
    }
}