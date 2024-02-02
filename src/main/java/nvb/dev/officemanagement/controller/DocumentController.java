package nvb.dev.officemanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.mapper.DocumentMapper;
import nvb.dev.officemanagement.model.dto.DocumentDto;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/docManagement")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @PostMapping(path = "/offices/{officeId}/documents")
    public ResponseEntity<DocumentDto> createDocument(@PathVariable long officeId,
                                                      @RequestBody @Valid DocumentDto documentDto) {
        DocumentEntity documentEntity = documentMapper.toDocumentEntity(documentDto);
        DocumentEntity savedDoc = documentService.createDocument(officeId, documentEntity);
        return new ResponseEntity<>(documentMapper.toDocumentDto(savedDoc), HttpStatus.CREATED);
    }

    @GetMapping(path = "/documents/{documentId}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable long documentId) {
        Optional<DocumentEntity> foundDoc = documentService.getDocumentById(documentId);
        return foundDoc.map(documentEntity -> {
            DocumentDto documentDto = documentMapper.toDocumentDto(documentEntity);
            return new ResponseEntity<>(documentDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/documents")
    public ResponseEntity<DocumentDto> getDocumentByTitle(@RequestParam(name = "title") String title) {
        Optional<DocumentEntity> foundDoc = documentService.getDocumentByTitle(title);
        return foundDoc.map(documentEntity -> {
            DocumentDto documentDto = documentMapper.toDocumentDto(documentEntity);
            return new ResponseEntity<>(documentDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/offices/{officeId}/documents")
    public ResponseEntity<List<DocumentDto>> getAllDocumentsByOfficeId(@PathVariable long officeId) {
        List<DocumentEntity> documents = documentService.getAllDocumentsByOfficeId(officeId);
        List<DocumentDto> documentDtoList = documents.stream().map(documentMapper::toDocumentDto).toList();
        return new ResponseEntity<>(documentDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/documents/{documentId}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable long documentId,
                                                      @RequestBody @Valid DocumentDto documentDto) {
        if (!documentService.isExists(documentId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        DocumentEntity documentEntity = documentMapper.toDocumentEntity(documentDto);
        DocumentEntity updatedDocument = documentService.updateDocument(documentId, documentEntity);
        return new ResponseEntity<>(documentMapper.toDocumentDto(updatedDocument), HttpStatus.OK);
    }

    @PatchMapping(path = "/documents/{documentId}")
    public ResponseEntity<DocumentDto> partialUpdate(@PathVariable long documentId,
                                                     @RequestBody @Valid DocumentDto documentDto) {
        if (!documentService.isExists(documentId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        DocumentEntity documentEntity = documentMapper.toDocumentEntity(documentDto);
        DocumentEntity partialUpdate = documentService.partialUpdate(documentId, documentEntity);
        return new ResponseEntity<>(documentMapper.toDocumentDto(partialUpdate), HttpStatus.OK);
    }

    @DeleteMapping(path = "/documents/{documentId}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable long documentId) {
        documentService.deleteDocument(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/offices/{officeId}/documents")
    public ResponseEntity<HttpStatus> deleteAllDocumentsOfOffice(@PathVariable long officeId) {
        documentService.deleteAllDocumentsOfOffice(officeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
