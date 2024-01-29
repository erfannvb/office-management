package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.DocumentDto;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {

    @Mapping(source = "documentEntity.id", target = "id")
    @Mapping(source = "documentEntity.title", target = "title")
    @Mapping(source = "documentEntity.description", target = "description")
    @Mapping(source = "documentEntity.managers", target = "managers")
    @Mapping(source = "documentEntity.clerks", target = "clerks")
    @Mapping(source = "documentEntity.office", target = "office")
    DocumentDto toDocumentDto(DocumentEntity documentEntity);

    @Mapping(source = "documentDto.id", target = "id")
    @Mapping(source = "documentDto.title", target = "title")
    @Mapping(source = "documentDto.description", target = "description")
    @Mapping(source = "documentDto.managers", target = "managers")
    @Mapping(source = "documentDto.clerks", target = "clerks")
    @Mapping(source = "documentDto.office", target = "office")
    DocumentEntity toDocumentEntity(DocumentDto documentDto);

}
