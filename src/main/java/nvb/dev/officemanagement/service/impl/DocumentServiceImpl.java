package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.repository.DocumentRepository;
import nvb.dev.officemanagement.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentEntity createDocument(DocumentEntity document) {
        return documentRepository.save(document);
    }

    @Override
    public DocumentEntity updateDocument(long id, DocumentEntity document) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {

            DocumentEntity currentDoc = optionalDocument.get();

            currentDoc.setTitle(document.getTitle());
            currentDoc.setDescription(document.getDescription());

            return documentRepository.save(currentDoc);

        } else {
            throw new EntityNotFoundException(DocumentEntity.class, id);
        }
    }

    @Override
    public List<DocumentEntity> findAll() {
        List<DocumentEntity> documentEntityList = documentRepository.findAll();
        if (documentEntityList.isEmpty()) throw new NoDataFoundException();
        return documentEntityList;
    }

    @Override
    public Optional<DocumentEntity> findDocumentById(long id) {
        return Optional.ofNullable(documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DocumentEntity.class, id)));
    }

    @Override
    public boolean isExists(long id) {
        return documentRepository.existsById(id);
    }

    @Override
    public DocumentEntity partialUpdate(long id, DocumentEntity document) {
        document.setId(id);

        return documentRepository.findById(id).map(existingDocument -> {

            Optional.ofNullable(document.getTitle()).ifPresent(existingDocument::setTitle);
            Optional.ofNullable(document.getDescription()).ifPresent(existingDocument::setDescription);

            return documentRepository.save(existingDocument);

        }).orElseThrow(() -> new EntityNotFoundException(DocumentEntity.class, id));
    }

    @Override
    public void deleteById(long id) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            documentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(DocumentEntity.class, id);
        }
    }
}
