package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.UserDto;
import nvb.dev.officemanagement.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.username", target = "username")
    @Mapping(source = "userEntity.password", target = "password")
    @Mapping(source = "userEntity.userRole", target = "userRole")
    UserDto toUserDto(UserEntity userEntity);

    @Mapping(source = "userDto.id", target = "id")
    @Mapping(source = "userDto.firstName", target = "firstName")
    @Mapping(source = "userDto.lastName", target = "lastName")
    @Mapping(source = "userDto.username", target = "username")
    @Mapping(source = "userDto.password", target = "password")
    @Mapping(source = "userDto.userRole", target = "userRole")
    UserEntity toUserEntity(UserDto userDto);

}
