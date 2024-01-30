package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.OfficeDto;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfficeMapper {

    @Mapping(source = "officeEntity.id", target = "id")
    @Mapping(source = "officeEntity.officeName", target = "officeName")
    @Mapping(source = "officeEntity.officeCode", target = "officeCode")
    @Mapping(source = "officeEntity.officePhoneNumber", target = "officePhoneNumber")
    @Mapping(source = "officeEntity.address", target = "address")
    OfficeDto toOfficeDto(OfficeEntity officeEntity);

    @Mapping(source = "officeDto.id", target = "id")
    @Mapping(source = "officeDto.officeName", target = "officeName")
    @Mapping(source = "officeDto.officeCode", target = "officeCode")
    @Mapping(source = "officeDto.officePhoneNumber", target = "officePhoneNumber")
    @Mapping(source = "officeDto.address", target = "address")
    OfficeEntity toOfficeEntity(OfficeDto officeDto);

}
