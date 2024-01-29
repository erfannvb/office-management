package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.repository.DocumentRepository;
import nvb.dev.officemanagement.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidDocument;
import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedDocument;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DocumentServiceTest {

    @Mock
    DocumentRepository documentRepository;

    @InjectMocks
    DocumentServiceImpl documentService;

    @Test
    @DisplayName("test that create document saves document")
    void testThatCreateDocumentSavesDocument() {
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        DocumentEntity savedDoc = documentService.createDocument(anyValidDocument());

        assertEquals("dummy", savedDoc.getTitle());
        assertEquals("dummy", savedDoc.getDescription());

        verify(documentRepository, atLeastOnce()).save(any(DocumentEntity.class));
    }

    @Test
    @DisplayName("test that update document updates the existing document")
    void testThatUpdateDocumentUpdatesTheExistingDocument() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(anyValidDocument()));
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidUpdatedDocument());

        DocumentEntity updatedDocument = documentService.updateDocument(1L, anyValidUpdatedDocument());

        assertEquals(anyValidUpdatedDocument().getTitle(), updatedDocument.getTitle());
        assertEquals(anyValidUpdatedDocument().getDescription(), updatedDocument.getDescription());

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).save(any(DocumentEntity.class));
    }

    @Test
    @DisplayName("test that update document cannot update the non existing document")
    void testThatUpdateDocumentCannotUpdateTheNonExistingDocument() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> documentService.updateDocument(1L, anyValidDocument()));

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    @DisplayName("test that find all returns list of documents")
    void testThatFindAllReturnsListOfDocuments() {
        List<DocumentEntity> documentEntityList = List.of(anyValidDocument(), anyValidDocument());

        when(documentRepository.findAll()).thenReturn(documentEntityList);

        List<DocumentEntity> result = documentService.findAll();

        assertEquals(2, result.size());
        verify(documentRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find all returns empty list")
    void testThatFindAllReturnsEmptyList() {
        when(documentRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> documentService.findAll());
        verify(documentRepository, atLeastOnce()).findAll();
    }

    @Test
    @DisplayName("test that find document by id returns document")
    void testThatFindDocumentByIdReturnsDocument() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(anyValidDocument()));

        Optional<DocumentEntity> result = documentService.findDocumentById(1L);

        assertEquals("dummy", result.get().getTitle());
        assertEquals("dummy", result.get().getDescription());

        verify(documentRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that find document by id returns nothing")
    void testThatFindDocumentByIdReturnsNothing() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> documentService.findDocumentById(1L));
        verify(documentRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns true")
    void testThatIsExistsReturnsTrue() {
        when(documentRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = documentService.isExists(1L);

        assertTrue(exists);
        verify(documentRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that is exists returns false")
    void testThatIsExistsReturnsFalse() {
        when(documentRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = documentService.isExists(1L);

        assertFalse(exists);
        verify(documentRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    @DisplayName("test that partial update updates the existing document")
    void testThatPartialUpdateUpdatesTheExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(anyValidDocument()));
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidUpdatedDocument());

        DocumentEntity result = documentService.partialUpdate(1L, anyValidUpdatedDocument());

        assertEquals(anyValidUpdatedDocument().getTitle(), result.getTitle());

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).save(any(DocumentEntity.class));
    }

    @Test
    @DisplayName("test that partial update cannot update the non existing document")
    void testThatPartialUpdateCannotUpdateTheNonExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.partialUpdate(1L, anyValidUpdatedDocument()));

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    @DisplayName("test that delete by id deletes the existing document")
    void testThatDeleteByIdDeletesTheExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(anyValidDocument()));

        documentService.deleteById(1L);

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    @DisplayName("test that delete by id cannot delete the non existing document")
    void testThatDeleteByIdCannotDeleteTheNonExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> documentService.deleteById(1L));

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).deleteById(anyLong());
    }

}