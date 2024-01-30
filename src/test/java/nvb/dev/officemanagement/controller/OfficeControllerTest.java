package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.mapper.OfficeMapper;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.service.OfficeService;
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

import static nvb.dev.officemanagement.MotherObject.anyValidOffice;
import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedOffice;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class OfficeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OfficeMapper officeMapper;

    @MockBean
    OfficeService officeService;

    @Test
    void testThatCreateOfficeSuccessfullyReturnsHttp201Created() throws Exception {
        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(anyValidOffice());

        String jsonOffice = objectMapper.writeValueAsString(anyValidOffice());

        mockMvc.perform(post("/api/v1/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOffice)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("dummy"));
    }

    @Test
    void testThatCreateOfficeReturnsHttp400BadRequestWhenOfficeNameIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeName("");

        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(officeEntity);

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateOfficeReturnsHttp400BadRequestWhenOfficeCodeIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeCode("");

        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(officeEntity);

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateOfficeReturnsHttp400BadRequestWhenOfficePhoneNumberIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficePhoneNumber("");

        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(officeEntity);

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateOfficeReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeName("");
        officeEntity.setOfficeCode("");
        officeEntity.setOfficePhoneNumber("");

        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(officeEntity);

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetAllOfficesSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of(anyValidOffice(), anyValidOffice()));

        mockMvc.perform(get("/api/v1/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].officeName").value("dummy"));
    }

    @Test
    void testThatGetOfficeByIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.getOfficeById(anyLong())).thenReturn(Optional.of(anyValidOffice()));

        mockMvc.perform(get("/api/v1/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("dummy"));
    }

    @Test
    void testThatGetOfficeByIdReturnsHttp404NotFound() throws Exception {
        when(officeService.getOfficeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatGetOfficeByOfficeNameSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.getOfficeByOfficeName(anyString())).thenReturn(Optional.of(anyValidOffice()));

        mockMvc.perform(get("/api/v1/officesByName/dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("dummy"));
    }

    @Test
    void testThatGetOfficeByOfficeNameReturnsHttp404NotFound() throws Exception {
        when(officeService.getOfficeByOfficeName(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/officesByName/wrong")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateOfficeSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(true);

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(put("/api/v1/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOffice)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("updatedDummy"));
    }

    @Test
    void testThatUpdateOfficeReturnsHttp404NotFound() throws Exception {
        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(false);

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(put("/api/v1/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOffice)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatPartialUpdateSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.partialUpdate(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(true);

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(patch("/api/v1/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOffice)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.officeName").value("updatedDummy"));
    }

    @Test
    void testThatPartialUpdateReturnsHttp404NotFound() throws Exception {
        when(officeService.partialUpdate(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(false);

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(patch("/api/v1/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOffice)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatDeleteOfficeReturnsHttp204ForExistingOffice() throws Exception {
        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(anyValidOffice());

        mockMvc.perform(delete("/api/v1/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteOfficeReturnsHttp204ForNonExistingOffice() throws Exception {
        mockMvc.perform(delete("/api/v1/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllOfficesReturns204ForExistingOffices() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of(anyValidOffice(), anyValidOffice()));

        mockMvc.perform(delete("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllOfficesReturns204ForNonExistingOffices() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of());

        mockMvc.perform(delete("/api/v1/offices")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}