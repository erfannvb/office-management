package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.mapper.ManagerMapper;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.service.ManagerService;
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

import static nvb.dev.officemanagement.MotherObject.*;
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
class ManagerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ManagerMapper managerMapper;

    @MockBean
    ManagerService managerService;

    @Test
    void testThatCreateManagerSuccessfullyReturnsHttp201Created() throws Exception {
        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(anyValidManager());

        String jsonManager = objectMapper.writeValueAsString(anyValidManager());

        mockMvc.perform(post("/api/v1/offices/1/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonManager)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatCreateManagerReturnsHttp400BadRequestWhenFirstNameIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setFirstName("");

        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(managerEntity);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(post("/api/v1/offices/1/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateManagerReturnsHttp400BadRequestWhenLastNameIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setLastName("");

        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(managerEntity);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(post("/api/v1/offices/1/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateManagerReturnsHttp400BadRequestWhenDepartmentIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setDepartment("");

        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(managerEntity);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(post("/api/v1/offices/1/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateManagerReturnsHttp400BadRequestWhenAgeIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setAge(12);

        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(managerEntity);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(post("/api/v1/offices/1/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateManagerReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setFirstName("");
        managerEntity.setLastName("");
        managerEntity.setDepartment("");
        managerEntity.setAge(10);

        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(managerEntity);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(post("/api/v1/offices/1/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetManagerByIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(managerService.getManagerById(anyLong())).thenReturn(Optional.of(anyValidManager()));

        mockMvc.perform(get("/api/v1/managers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatGetManagerByIdReturnsHttp404NotFound() throws Exception {
        when(managerService.getManagerById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/managers/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAllManagersByOfficeIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(managerService.getAllManagersByOfficeId(anyLong())).thenReturn(List.of(
                anyValidManager(), anyValidManager()
        ));

        mockMvc.perform(get("/api/v1/offices/1/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].firstName").value("dummy"));
    }

    @Test
    void testThatUpdateManagerSuccessfullyReturnsHttp200Ok() throws Exception {
        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(anyValidUpdatedManager());
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(anyValidUpdatedManager());

        mockMvc.perform(put("/api/v1/managers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonManager)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("updatedDummy"));
    }

    @Test
    void testThatUpdateManagerReturnsHttp404NotFound() throws Exception {
        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(anyValidUpdatedManager());
        when(managerService.isExists(anyLong())).thenReturn(false);

        String jsonManager = objectMapper.writeValueAsString(anyValidUpdatedManager());

        mockMvc.perform(put("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateManagerReturnsHttp400BadRequestWhenFirstNameIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setFirstName("");

        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(put("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateManagerReturnsHttp400BadRequestWhenLastNameIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setLastName("");

        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(put("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateManagerReturnsHttp400BadRequestWhenDepartmentIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setDepartment("");

        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(put("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateManagerReturnsHttp400BadRequestWhenAgeIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setAge(12);

        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(put("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateManagerReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setFirstName("");
        managerEntity.setLastName("");
        managerEntity.setDepartment("");
        managerEntity.setAge(10);

        when(managerService.updateManager(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(put("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateSuccessfullyReturnsHttp200Ok() throws Exception {
        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(anyValidUpdatedManager());
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(anyValidUpdatedManager());

        mockMvc.perform(patch("/api/v1/managers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonManager)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("updatedDummy"));
    }

    @Test
    void testThatPartialUpdateReturnsHttp404NotFound() throws Exception {
        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(anyValidUpdatedManager());
        when(managerService.isExists(anyLong())).thenReturn(false);

        String jsonManager = objectMapper.writeValueAsString(anyValidUpdatedManager());

        mockMvc.perform(patch("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenFirstNameIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setFirstName("");

        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(patch("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenLastNameIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setLastName("");

        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(patch("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenDepartmentIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setDepartment("");

        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(patch("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenAgeIsEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setAge(12);

        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(patch("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        ManagerEntity managerEntity = anyValidManager();
        managerEntity.setFirstName("");
        managerEntity.setLastName("");
        managerEntity.setDepartment("");
        managerEntity.setAge(12);

        when(managerService.partialUpdate(anyLong(), any(ManagerEntity.class)))
                .thenReturn(managerEntity);
        when(managerService.isExists(anyLong())).thenReturn(true);

        String jsonManager = objectMapper.writeValueAsString(managerEntity);

        mockMvc.perform(patch("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonManager)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatDeleteManagerReturnsSuccessfullyHttp204NoContentForExisting() throws Exception {
        when(managerService.createManager(anyLong(), any(ManagerEntity.class))).thenReturn(anyValidManager());

        mockMvc.perform(delete("/api/v1/managers/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteManagerReturnsHttp204NoContentForNonExistingManager() throws Exception {
        mockMvc.perform(delete("/api/v1/managers/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllManagersOfOfficeSuccessfullyReturnsHttp204NoContentForExistingManager() throws Exception {
        when(managerService.createManager(anyLong(),any(ManagerEntity.class))).thenReturn(anyValidManager());

        mockMvc.perform(delete("/api/v1/offices/1/managers")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllManagersOfOfficeReturnsHttp204NoContentForNonExistingManager() throws Exception {
        mockMvc.perform(delete("/api/v1/offices/99/managers")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}