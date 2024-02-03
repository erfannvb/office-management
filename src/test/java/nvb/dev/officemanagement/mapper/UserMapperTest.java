package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.UserDto;
import nvb.dev.officemanagement.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.officemanagement.MotherObject.anyValidUser;
import static nvb.dev.officemanagement.MotherObject.anyValidUserDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserMapperImpl.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void toUserDto() {
        UserDto userDto = userMapper.toUserDto(anyValidUser());

        assertEquals(anyValidUser().getId(), userDto.getId());
        assertEquals(anyValidUser().getUsername(), userDto.getUsername());
        assertEquals(anyValidUser().getPassword(), userDto.getPassword());
    }

    @Test
    void toUserDto_IsNull() {
        UserDto userDto = userMapper.toUserDto(null);
        assertNull(userDto);
    }

    @Test
    void toUserEntity() {
        UserEntity userEntity = userMapper.toUserEntity(anyValidUserDto());

        assertEquals(anyValidUserDto().getId(), userEntity.getId());
        assertEquals(anyValidUserDto().getUsername(), userEntity.getUsername());
        assertEquals(anyValidUserDto().getPassword(), userEntity.getPassword());
    }

    @Test
    void toUserEntity_IsNull() {
        UserEntity userEntity = userMapper.toUserEntity(null);
        assertNull(userEntity);
    }
}