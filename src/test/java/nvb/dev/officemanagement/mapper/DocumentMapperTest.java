package nvb.dev.officemanagement.mapper;

import nvb.dev.officemanagement.model.dto.DocumentDto;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.officemanagement.MotherObject.anyValidDocument;
import static nvb.dev.officemanagement.MotherObject.anyValidDocumentDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DocumentMapperImpl.class})
class DocumentMapperTest {

    @Autowired
    DocumentMapper documentMapper;

    @Test
    void toDocumentDto() {
        DocumentDto documentDto = documentMapper.toDocumentDto(anyValidDocument());

        assertEquals(anyValidDocument().getId(), documentDto.getId());
        assertEquals(anyValidDocument().getTitle(), documentDto.getTitle());
        assertEquals(anyValidDocument().getDescription(), documentDto.getDescription());
        assertEquals(anyValidDocument().getManagers(), documentDto.getManagers());
        assertEquals(anyValidDocument().getClerks(), documentDto.getClerks());
        assertEquals(anyValidDocument().getOffice(), documentDto.getOffice());
    }

    @Test
    void toDocumentDto_IsNull() {
        DocumentDto documentDto = documentMapper.toDocumentDto(null);
        assertNull(documentDto);
    }

    @Test
    void toDocumentEntity() {
        DocumentEntity documentEntity = documentMapper.toDocumentEntity(anyValidDocumentDto());

        assertEquals(anyValidDocumentDto().getId(), documentEntity.getId());
        assertEquals(anyValidDocumentDto().getTitle(), documentEntity.getTitle());
        assertEquals(anyValidDocumentDto().getDescription(), documentEntity.getDescription());
        assertEquals(anyValidDocumentDto().getManagers(), documentEntity.getManagers());
        assertEquals(anyValidDocumentDto().getClerks(), documentEntity.getClerks());
        assertEquals(anyValidDocumentDto().getOffice(), documentEntity.getOffice());
    }

    @Test
    void toDocumentEntity_IsNull() {
        DocumentEntity documentEntity = documentMapper.toDocumentEntity(null);
        assertNull(documentEntity);
    }
}