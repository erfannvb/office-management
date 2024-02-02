package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.DocumentRepository;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final OfficeRepository officeRepository;

    @Override
    public DocumentEntity createDocument(long officeId, DocumentEntity document) {
        OfficeEntity officeEntity = unwrapOffice(officeRepository.findById(officeId), officeId);
        document.setOffice(officeEntity);
        return documentRepository.save(document);
    }

    @Override
    public Optional<DocumentEntity> getDocumentById(long documentId) {
        return Optional.ofNullable(documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(DocumentEntity.class, documentId)));
    }

    @Override
    public Optional<DocumentEntity> getDocumentByTitle(String title) {
        return Optional.ofNullable(documentRepository.findByTitle(title)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<DocumentEntity> getAllDocumentsByOfficeId(long officeId) {
        return documentRepository.findAllByOfficeId(officeId);
    }

    @Override
    public DocumentEntity updateDocument(long documentId, DocumentEntity document) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isPresent()) {

            DocumentEntity currentDocument = optionalDocument.get();

            currentDocument.setTitle(document.getTitle());
            currentDocument.setDescription(document.getDescription());

            return documentRepository.save(currentDocument);

        } else {
            throw new EntityNotFoundException(DocumentEntity.class, documentId);
        }
    }

    @Override
    public DocumentEntity partialUpdate(long documentId, DocumentEntity document) {
        document.setId(documentId);

        return documentRepository.findById(documentId).map(existingDocument -> {

            Optional.ofNullable(document.getTitle()).ifPresent(existingDocument::setTitle);
            Optional.ofNullable(document.getDescription()).ifPresent(existingDocument::setDescription);

            return documentRepository.save(existingDocument);

        }).orElseThrow(() -> new EntityNotFoundException(DocumentEntity.class, documentId));
    }

    @Override
    public void deleteDocument(long documentId) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isPresent()) {
            documentRepository.deleteById(documentId);
        } else {
            throw new EntityNotFoundException(DocumentEntity.class, documentId);
        }
    }

    @Override
    public void deleteAllDocumentsOfOffice(long officeId) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(officeId);
        if (optionalOffice.isPresent()) {
            documentRepository.deleteByOfficeId(officeId);
        } else {
            throw new EntityNotFoundException(OfficeEntity.class, officeId);
        }
    }

    @Override
    public boolean isExists(long documentId) {
        return documentRepository.existsById(documentId);
    }

    private static OfficeEntity unwrapOffice(Optional<OfficeEntity> entity, long officeId) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(OfficeEntity.class, officeId);
    }
}
