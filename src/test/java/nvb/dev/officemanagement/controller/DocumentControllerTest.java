package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.mapper.DocumentMapper;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidDocument;
import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedDocument;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class DocumentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DocumentMapper documentMapper;

    @MockBean
    DocumentService documentService;

    @Test
    void testThatCreateDocumentSuccessfullyReturnsHttp201Created() throws Exception {
        when(documentService.createDocument(anyLong(), any(DocumentEntity.class))).thenReturn(anyValidDocument());

        String jsonDoc = objectMapper.writeValueAsString(anyValidDocument());

        mockMvc.perform(post("/api/v1/offices/1/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDoc)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("dummy"));
    }

    @Test
    void testThatCreateDocumentReturns400BadRequestWhenTitleIsEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setTitle("");

        when(documentService.createDocument(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(post("/api/v1/offices/1/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateDocumentReturns400BadRequestWhenDescriptionIsEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setDescription("");

        when(documentService.createDocument(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(post("/api/v1/offices/1/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateDocumentReturns400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setTitle("");
        documentEntity.setDescription("");

        when(documentService.createDocument(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(post("/api/v1/offices/1/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetDocumentByIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(documentService.getDocumentById(anyLong())).thenReturn(Optional.of(anyValidDocument()));

        mockMvc.perform(get("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatGetDocumentByIdReturnsHttp404NotFound() throws Exception {
        when(documentService.getDocumentById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/documents/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAllDocumentsByOfficeIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(documentService.getAllDocumentsByOfficeId(anyLong())).thenReturn(
                List.of(anyValidDocument(), anyValidDocument())
        );

        mockMvc.perform(get("/api/v1/offices/1/documents")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatUpdateDocumentSuccessfullyReturnsHttp200Ok() throws Exception {
        when(documentService.updateDocument(anyLong(), any(DocumentEntity.class)))
                .thenReturn(anyValidUpdatedDocument());
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(anyValidDocument());

        mockMvc.perform(put("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatUpdateDocumentReturnsHttp404NotFound() throws Exception {
        when(documentService.updateDocument(anyLong(), any(DocumentEntity.class)))
                .thenReturn(anyValidUpdatedDocument());
        when(documentService.isExists(anyLong())).thenReturn(false);

        String jsonDoc = objectMapper.writeValueAsString(anyValidDocument());

        mockMvc.perform(put("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateDocumentReturnsHttp400BadRequestWhenTitleIsEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setTitle("");

        when(documentService.updateDocument(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(put("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateDocumentReturnsHttp400BadRequestWhenDescriptionIsEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setDescription("");

        when(documentService.updateDocument(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(put("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateDocumentReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setTitle("");
        documentEntity.setDescription("");

        when(documentService.updateDocument(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(put("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateSuccessfullyReturnsHttp200Ok() throws Exception {
        when(documentService.partialUpdate(anyLong(), any(DocumentEntity.class))).thenReturn(anyValidDocument());
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(anyValidDocument());

        mockMvc.perform(patch("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatPartialUpdateReturnsHttp404NotFound() throws Exception {
        when(documentService.partialUpdate(anyLong(), any(DocumentEntity.class))).thenReturn(anyValidDocument());
        when(documentService.isExists(anyLong())).thenReturn(false);

        String jsonDoc = objectMapper.writeValueAsString(anyValidDocument());

        mockMvc.perform(patch("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenTitleIsEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setTitle("");

        when(documentService.partialUpdate(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(patch("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenDescriptionIsEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setDescription("");

        when(documentService.partialUpdate(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(patch("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        DocumentEntity documentEntity = anyValidDocument();
        documentEntity.setTitle("");
        documentEntity.setDescription("");

        when(documentService.partialUpdate(anyLong(), any(DocumentEntity.class))).thenReturn(documentEntity);
        when(documentService.isExists(anyLong())).thenReturn(true);

        String jsonDoc = objectMapper.writeValueAsString(documentEntity);

        mockMvc.perform(patch("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDoc)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatDeleteDocumentReturnsHttp204NoContentWhenDocumentExists() throws Exception {
        when(documentService.createDocument(anyLong(), any(DocumentEntity.class)))
                .thenReturn(anyValidDocument());

        mockMvc.perform(delete("/api/v1/documents/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteDocumentReturnsHttp204NoContentWhenDocumentDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/documents/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllDocumentsOfOfficeReturnsHttp204NoContentWhenOfficeExists() throws Exception {
        when(documentService.createDocument(anyLong(), any(DocumentEntity.class)))
                .thenReturn(anyValidDocument());

        mockMvc.perform(delete("/api/v1/offices/1/documents")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllDocumentsOfOfficeReturnsHttp204NoContentWhenOfficeDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/offices/99/documents")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}