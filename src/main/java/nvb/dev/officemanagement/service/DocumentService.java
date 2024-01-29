package nvb.dev.officemanagement.service;

import nvb.dev.officemanagement.model.entity.DocumentEntity;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    DocumentEntity createDocument(DocumentEntity document);

    DocumentEntity updateDocument(long id, DocumentEntity document);

    List<DocumentEntity> findAll();

    Optional<DocumentEntity> findDocumentById(long id);

    boolean isExists(long id);

    DocumentEntity partialUpdate(long id, DocumentEntity document);

    void deleteById(long id);

}
