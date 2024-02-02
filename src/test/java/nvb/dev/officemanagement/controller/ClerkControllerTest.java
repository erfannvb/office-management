package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.mapper.ClerkMapper;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.service.ClerkService;
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

import static nvb.dev.officemanagement.MotherObject.anyValidClerk;
import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedClerk;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ClerkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ClerkMapper clerkMapper;

    @MockBean
    ClerkService clerkService;

    @Test
    void testThatCreateClerkSuccessfullyReturns201Created() throws Exception {
        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(anyValidClerk());

        String jsonClerk = objectMapper.writeValueAsString(anyValidClerk());

        mockMvc.perform(post("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("erfan", "password123"))
                        .content(jsonClerk)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatCreateClerkReturnsHttp400BadRequestWhenFirstNameIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setFirstName("");

        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(post("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateClerkReturnsHttp400BadRequestWhenLastNameIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setLastName("");

        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(post("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateClerkReturnsHttp400BadRequestWhenDepartmentIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setDepartment("");

        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(post("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateClerkReturnsHttp400BadRequestWhenAgeIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setAge(12);

        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(post("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateClerkReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setFirstName("");
        clerkEntity.setLastName("");
        clerkEntity.setDepartment("");
        clerkEntity.setAge(12);

        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(post("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetAllClerksByOfficeIdAndManagerIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(clerkService.getAllClerksByOfficeIdAndManagerId(anyLong(), anyLong()))
                .thenReturn(List.of(anyValidClerk(), anyValidClerk()));

        mockMvc.perform(get("/api/v1/clerkManagement/offices/1/managers/1/clerks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("erfan", "password123"))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].firstName").value("dummy"));
    }

    @Test
    void testThatGetClerkByIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(clerkService.getClerkById(anyLong())).thenReturn(Optional.of(anyValidClerk()));

        mockMvc.perform(get("/api/v1/clerkManagement/clerks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("erfan", "password123"))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatGetClerkByIdReturnsHttp404NotFound() throws Exception {
        when(clerkService.getClerkById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateClerkSuccessfullyReturnsHttp200Ok() throws Exception {
        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(anyValidUpdatedClerk());

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("erfan", "password123"))
                        .content(jsonClerk)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("updatedDummy"));
    }

    @Test
    void testThatUpdateClerkReturnsHttp404NotFound() throws Exception {
        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());
        when(clerkService.isExists(anyLong())).thenReturn(false);

        String jsonClerk = objectMapper.writeValueAsString(anyValidUpdatedClerk());

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateClerkReturnsHttp400BadRequestWhenFirstNameIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setFirstName("");

        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateClerkReturnsHttp400BadRequestWhenLastNameIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setLastName("");

        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateClerkReturnsHttp400BadRequestWhenDepartmentIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setDepartment("");

        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateClerkReturnsHttp400BadRequestWhenAgeIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setAge(12);

        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateClerkReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidClerk();
        clerkEntity.setFirstName("");
        clerkEntity.setLastName("");
        clerkEntity.setDepartment("");
        clerkEntity.setAge(12);

        when(clerkService.updateClerk(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(put("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateSuccessfullyReturnsHttp200Ok() throws Exception {
        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(anyValidUpdatedClerk());

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("erfan", "password123"))
                        .content(jsonClerk)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("updatedDummy"));
    }

    @Test
    void testThatPartialUpdateReturnsHttp404NotFound() throws Exception {
        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(anyValidUpdatedClerk());
        when(clerkService.isExists(anyLong())).thenReturn(false);

        String jsonClerk = objectMapper.writeValueAsString(anyValidUpdatedClerk());

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/99")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenFirstNameIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidUpdatedClerk();
        clerkEntity.setFirstName("");

        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenLastNameIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidUpdatedClerk();
        clerkEntity.setLastName("");

        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenDepartmentIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidUpdatedClerk();
        clerkEntity.setDepartment("");

        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenAgeIsEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidUpdatedClerk();
        clerkEntity.setAge(10);

        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        ClerkEntity clerkEntity = anyValidUpdatedClerk();
        clerkEntity.setFirstName("");
        clerkEntity.setLastName("");
        clerkEntity.setDepartment("");
        clerkEntity.setAge(13);

        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(clerkEntity);
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(clerkEntity);

        mockMvc.perform(patch("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
                .content(jsonClerk)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatDeleteClerkReturnsHttp204WhenClerkExists() throws Exception {
        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class))).thenReturn(anyValidClerk());

        mockMvc.perform(delete("/api/v1/clerkManagement/clerks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteClerkReturnsHttp204WhenClerkDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/clerkManagement/clerks/99")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllClerksOfManagerReturnsHttp204WhenManagerExists() throws Exception {
        when(clerkService.createClerk(anyLong(), anyLong(), any(ClerkEntity.class)))
                .thenReturn(anyValidClerk());

        mockMvc.perform(delete("/api/v1/clerkManagement/managers/1/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllClerksOfManagerReturnsHttp204WhenManagerDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/clerkManagement/managers/99/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("erfan", "password123"))
        ).andExpect(status().isNoContent());
    }

}