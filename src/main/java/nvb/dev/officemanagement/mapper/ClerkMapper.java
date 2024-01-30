package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.ClerkDto;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClerkMapper {

    @Mapping(source = "clerkEntity.id", target = "id")
    @Mapping(source = "clerkEntity.firstName", target = "firstName")
    @Mapping(source = "clerkEntity.lastName", target = "lastName")
    @Mapping(source = "clerkEntity.department", target = "department")
    @Mapping(source = "clerkEntity.age", target = "age")
    @Mapping(source = "clerkEntity.manager", target = "manager")
    @Mapping(source = "clerkEntity.office", target = "office")
    ClerkDto toClerkDto(ClerkEntity clerkEntity);

    @Mapping(source = "clerkDto.id", target = "id")
    @Mapping(source = "clerkDto.firstName", target = "firstName")
    @Mapping(source = "clerkDto.lastName", target = "lastName")
    @Mapping(source = "clerkDto.department", target = "department")
    @Mapping(source = "clerkDto.age", target = "age")
    @Mapping(source = "clerkDto.manager", target = "manager")
    @Mapping(source = "clerkDto.office", target = "office")
    ClerkEntity toClerkEntity(ClerkDto clerkDto);

}
