package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.UserDto;
import nvb.dev.officemanagement.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "userEntity.username", target = "username")
    @Mapping(source = "userEntity.password", target = "password")
    @Mapping(source = "userEntity.role", target = "role")
    UserDto toUserDto(UserEntity userEntity);

    @Mapping(source = "userDto.username", target = "username")
    @Mapping(source = "userDto.password", target = "password")
    @Mapping(source = "userDto.role", target = "role")
    UserEntity toUserEntity(UserDto userDto);

}
