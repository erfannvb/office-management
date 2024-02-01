package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.DocumentEntity;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    DocumentEntity createDocument(long officeId, DocumentEntity document);

    Optional<DocumentEntity> getDocumentById(long documentId);

    List<DocumentEntity> getAllDocumentsByOfficeId(long officeId);

    DocumentEntity updateDocument(long documentId, DocumentEntity document);

    DocumentEntity partialUpdate(long documentId, DocumentEntity document);

    void deleteDocument(long documentId);

    void deleteAllDocumentsOfOffice(long officeId);

    boolean isExists(long documentId);

}
