package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.mapper.OfficeMapper;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.security.JwtService;
import nvb.dev.officemanagement.security.impl.UserServiceDetailsImpl;
import nvb.dev.officemanagement.service.OfficeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.*;
import static nvb.dev.officemanagement.constant.SecurityConstant.BEARER;
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

    @Autowired
    JwtService jwtService;

    @MockBean
    UserServiceDetailsImpl userServiceDetails;

    @MockBean
    OfficeService officeService;

    private String generateToken() {
        UserDetails user = User.builder()
                .username("dummy")
                .password("dummy")
                .roles("ADMIN")
                .build();
        return jwtService.generateToken(user);
    }

    @Test
    void testThatCreateOfficeSuccessfullyReturnsHttp201Created() throws Exception {
        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(anyValidOffice());
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(anyValidOffice());

        mockMvc.perform(post("/api/v1/officeManagement/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
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
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/officeManagement/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateOfficeReturnsHttp400BadRequestWhenOfficeCodeIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeCode("");

        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(officeEntity);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/officeManagement/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateOfficeReturnsHttp400BadRequestWhenOfficePhoneNumberIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficePhoneNumber("");

        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(officeEntity);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/officeManagement/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
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
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(post("/api/v1/officeManagement/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetAllOfficesSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of(anyValidOffice(), anyValidOffice()));
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/officeManagement/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].officeName").value("dummy"));
    }

    @Test
    void testThatGetOfficeByIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.getOfficeById(anyLong())).thenReturn(Optional.of(anyValidOffice()));
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/officeManagement/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("dummy"));
    }

    @Test
    void testThatGetOfficeByIdReturnsHttp404NotFound() throws Exception {
        when(officeService.getOfficeById(anyLong())).thenReturn(Optional.empty());
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/officeManagement/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatGetOfficeByOfficeNameSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.getOfficeByOfficeName(anyString())).thenReturn(Optional.of(anyValidOffice()));
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/officeManagement/officesByName/dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("dummy"));
    }

    @Test
    void testThatGetOfficeByOfficeNameReturnsHttp404NotFound() throws Exception {
        when(officeService.getOfficeByOfficeName(anyString())).thenReturn(Optional.empty());
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/officeManagement/officesByName/wrong")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateOfficeSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(put("/api/v1/officeManagement/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(jsonOffice)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.officeName").value("updatedDummy"));
    }

    @Test
    void testThatUpdateOfficeReturnsHttp404NotFound() throws Exception {
        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(false);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(put("/api/v1/officeManagement/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateOfficeReturnsHttp400BadRequestWhenOfficeNameIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeName("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(put("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateOfficeReturnsHttp400BadRequestWhenOfficeCodeIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeCode("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(put("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateOfficeReturnsHttp400BadRequestWhenOfficePhoneNumberIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficePhoneNumber("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(put("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateOfficeReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficePhoneNumber("");
        officeEntity.setOfficeName("");
        officeEntity.setOfficeCode("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(put("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateSuccessfullyReturnsHttp200Ok() throws Exception {
        when(officeService.partialUpdate(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(patch("/api/v1/officeManagement/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(jsonOffice)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.officeName").value("updatedDummy"));
    }

    @Test
    void testThatPartialUpdateReturnsHttp404NotFound() throws Exception {
        when(officeService.partialUpdate(anyLong(), any(OfficeEntity.class))).thenReturn(anyValidUpdatedOffice());
        when(officeService.isExists(anyLong())).thenReturn(false);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(anyValidUpdatedOffice());

        mockMvc.perform(patch("/api/v1/officeManagement/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenOfficeNameIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeName("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(patch("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenOfficeCodeIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeCode("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(patch("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenOfficePhoneNumberIsEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficePhoneNumber("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(patch("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        OfficeEntity officeEntity = anyValidOffice();
        officeEntity.setOfficeName("");
        officeEntity.setOfficePhoneNumber("");
        officeEntity.setOfficeCode("");

        when(officeService.updateOffice(anyLong(), any(OfficeEntity.class))).thenReturn(officeEntity);
        when(officeService.isExists(anyLong())).thenReturn(true);
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String jsonOffice = objectMapper.writeValueAsString(officeEntity);

        mockMvc.perform(patch("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
                .content(jsonOffice)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatDeleteOfficeReturnsHttp204ForExistingOffice() throws Exception {
        when(officeService.createOffice(any(OfficeEntity.class))).thenReturn(anyValidOffice());
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/v1/officeManagement/offices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteOfficeReturnsHttp204ForNonExistingOffice() throws Exception {
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/v1/officeManagement/offices/99")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllOfficesReturns204ForExistingOffices() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of(anyValidOffice(), anyValidOffice()));
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/v1/officeManagement/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteAllOfficesReturns204ForNonExistingOffices() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of());
        when(userServiceDetails.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/v1/officeManagement/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", BEARER + token)
        ).andExpect(status().isNoContent());
    }

}