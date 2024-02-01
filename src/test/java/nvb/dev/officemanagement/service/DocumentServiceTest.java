package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.repository.DocumentRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidDocument;
import static nvb.dev.officemanagement.MotherObject.anyValidOffice;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DocumentServiceTest {

    @Mock
    DocumentRepository documentRepository;

    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    DocumentServiceImpl documentService;

    @Test
    void testThatCreateDocumentSavesDocument() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        DocumentEntity savedDoc = documentService.createDocument(1L, anyValidDocument());

        assertEquals("dummy", savedDoc.getTitle());
        assertEquals("dummy", savedDoc.getDescription());

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).save(any(DocumentEntity.class));
    }

    @Test
    void testThatCreateDocumentCannotSaveDocumentWhenOfficeDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.createDocument(1L, anyValidDocument()));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    void testThatGetDocumentByIdReturnsDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(anyValidDocument()));

        Optional<DocumentEntity> optionalDocument = documentService.getDocumentById(1L);

        assertEquals("dummy", optionalDocument.get().getTitle());
        assertEquals("dummy", optionalDocument.get().getDescription());

        verify(documentRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetDocumentByIdReturnsNothing() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> documentService.getDocumentById(1L));
        verify(documentRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetAllDocumentsByOfficeIdReturnsListOfDocuments() {
        when(documentRepository.findAllByOfficeId(anyLong())).thenReturn(
                List.of(anyValidDocument(), anyValidDocument())
        );

        List<DocumentEntity> documents = documentService.getAllDocumentsByOfficeId(1L);

        assertEquals(2, documents.size());
        verify(documentRepository, atLeastOnce()).findAllByOfficeId(anyLong());
    }

    @Test
    void testThatUpdateDocumentUpdatesTheExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(anyValidDocument()));
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        DocumentEntity updatedDocument = documentService.updateDocument(1L, anyValidDocument());

        assertEquals("dummy", updatedDocument.getTitle());
        assertEquals("dummy", updatedDocument.getDescription());

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).save(any(DocumentEntity.class));
    }

    @Test
    void testThatUpdateDocumentUpdatesTheNonExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.updateDocument(1L, anyValidDocument()));

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    void testThatPartialUpdateDocumentUpdatesTheExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(anyValidDocument()));
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        DocumentEntity updatedDocument = documentService.partialUpdate(1L, anyValidDocument());

        assertEquals("dummy", updatedDocument.getTitle());
        assertEquals("dummy", updatedDocument.getDescription());

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).save(any(DocumentEntity.class));
    }

    @Test
    void testThatPartialUpdateDocumentUpdatesTheNonExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(anyValidDocument());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.partialUpdate(1L, anyValidDocument()));

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).save(any(DocumentEntity.class));
    }

    @Test
    void testThatDeleteDocumentDeletesTheExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(anyValidDocument()));

        documentService.deleteDocument(1L);

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteDocumentCannotDeleteTheNonExistingDocument() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> documentService.deleteDocument(1L));

        verify(documentRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteAllDocumentsOfOfficeDeletesAllDocuments() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.of(anyValidOffice()));

        documentService.deleteAllDocumentsOfOffice(1L);

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, atLeastOnce()).deleteByOfficeId(anyLong());
    }

    @Test
    void testThatDeleteAllDocumentsOfOfficeCannotDeleteWhenOfficeDoesNotExist() {
        when(officeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.deleteAllDocumentsOfOffice(1L));

        verify(officeRepository, atLeastOnce()).findById(anyLong());
        verify(documentRepository, never()).deleteByOfficeId(anyLong());
    }

    @Test
    void testThatIsExistsReturnsTrue() {
        when(documentRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = documentService.isExists(1L);

        assertTrue(exists);
        verify(documentRepository, atLeastOnce()).existsById(anyLong());
    }

    @Test
    void testThatIsExistsReturnsFalse() {
        when(documentRepository.existsById(anyLong())).thenReturn(false);

        boolean exists = documentService.isExists(1L);

        assertFalse(exists);
        verify(documentRepository, atLeastOnce()).existsById(anyLong());
    }

}