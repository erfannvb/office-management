package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.ManagerDto;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManagerMapper {

    @Mapping(source = "managerEntity.id", target = "id")
    @Mapping(source = "managerEntity.firstName", target = "firstName")
    @Mapping(source = "managerEntity.lastName", target = "lastName")
    @Mapping(source = "managerEntity.department", target = "department")
    @Mapping(source = "managerEntity.age", target = "age")
    @Mapping(source = "managerEntity.office", target = "office")
    ManagerDto toManagerDto(ManagerEntity managerEntity);

    @Mapping(source = "managerDto.id", target = "id")
    @Mapping(source = "managerDto.firstName", target = "firstName")
    @Mapping(source = "managerDto.lastName", target = "lastName")
    @Mapping(source = "managerDto.department", target = "department")
    @Mapping(source = "managerDto.age", target = "age")
    @Mapping(source = "managerDto.office", target = "office")
    ManagerEntity toManagerEntity(ManagerDto managerDto);

}
