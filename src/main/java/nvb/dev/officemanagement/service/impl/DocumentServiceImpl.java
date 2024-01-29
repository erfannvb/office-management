package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
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
    public DocumentEntity createUpdateDocument(long id, DocumentEntity document) {
        document.setId(id);
        return documentRepository.save(document);
    }

    @Override
    public List<DocumentEntity> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public Optional<DocumentEntity> findDocumentById(long id) {
        return documentRepository.findById(id);
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

        }).orElseThrow();
    }

    @Override
    public void deleteById(long id) {
        documentRepository.deleteById(id);
    }
}
