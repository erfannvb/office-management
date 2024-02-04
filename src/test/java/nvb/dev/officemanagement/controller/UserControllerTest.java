package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.mapper.UserMapper;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.service.UserService;
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

import static nvb.dev.officemanagement.MotherObject.anyValidUpdatedUser;
import static nvb.dev.officemanagement.MotherObject.anyValidUser;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserMapper userMapper;

    @MockBean
    UserService userService;

    @Test
    void testThatCreateMethodSuccessfullyReturnsHttp201Created() throws Exception {
        when(userService.createUser(any(UserEntity.class))).thenReturn(anyValidUser());

        String jsonUser = objectMapper.writeValueAsString(anyValidUser());

        mockMvc.perform(post("/api/v1/userManagement/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").value("dummy"))
                .andExpect(jsonPath("$.password").value("dummy"));
    }

    @Test
    void testThatCreateUserReturns400BadRequestWhenUsernameIsEmpty() throws Exception {
        UserEntity userEntity = anyValidUser();
        userEntity.setUsername("");

        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);

        String jsonUser = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(post("/api/v1/userManagement/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateUserReturns400BadRequestWhenPasswordIsEmpty() throws Exception {
        UserEntity userEntity = anyValidUser();
        userEntity.setPassword("");

        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);

        String jsonUser = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(post("/api/v1/userManagement/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatCreateUserReturns400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        UserEntity userEntity = anyValidUser();
        userEntity.setUsername("");
        userEntity.setPassword("");

        when(userService.createUser(any(UserEntity.class))).thenReturn(userEntity);

        String jsonUser = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(post("/api/v1/userManagement/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetUserByIdSuccessfullyReturnsHttp200Ok() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(anyValidUser()));

        mockMvc.perform(get("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatGetUserByIdReturnsHttp404NotFoundWhenUserDoesNotExist() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatGetUserByUsernameSuccessfullyReturnsHttp200Ok() throws Exception {
        when(userService.getUserByUsername(anyString())).thenReturn(Optional.of(anyValidUser()));

        mockMvc.perform(get("/api/v1/userManagement/users?username=dummy")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatGetUserByUsernameReturnsHttp404NotFoundWhenUserDoesNotExist() throws Exception {
        when(userService.getUserByUsername(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/userManagement/users?username=dummy")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAllUsersSuccessfullyReturnsHttp200Ok() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(anyValidUser(), anyValidUser()));

        mockMvc.perform(get("/api/v1/userManagement/users/all")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatUpdateUserSuccessfullyReturnsHttp200Ok() throws Exception {
        when(userService.isExists(anyLong())).thenReturn(true);
        when(userService.updateUser(anyLong(), any(UserEntity.class))).thenReturn(anyValidUpdatedUser());

        String jsonUser = objectMapper.writeValueAsString(anyValidUpdatedUser());

        mockMvc.perform(put("/api/v1/userManagement/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").value("updatedDummy"));
    }

    @Test
    void testThatUpdateUserReturnsHttp404NotFoundWhenUserDoesNotExist() throws Exception {
        when(userService.isExists(anyLong())).thenReturn(false);
        when(userService.updateUser(anyLong(), any(UserEntity.class))).thenReturn(anyValidUpdatedUser());

        String jsonUser = objectMapper.writeValueAsString(anyValidUpdatedUser());

        mockMvc.perform(put("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateUserReturnsHttp400BadRequestWhenUsernameIsEmpty() throws Exception {
        UserEntity userEntity = anyValidUpdatedUser();
        userEntity.setUsername("");

        when(userService.isExists(anyLong())).thenReturn(true);
        when(userService.updateUser(anyLong(), any(UserEntity.class))).thenReturn(userEntity);

        String jsonUser = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(put("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateUserReturnsHttp400BadRequestWhenPasswordIsEmpty() throws Exception {
        UserEntity userEntity = anyValidUpdatedUser();
        userEntity.setPassword("");

        when(userService.isExists(anyLong())).thenReturn(true);
        when(userService.updateUser(anyLong(), any(UserEntity.class))).thenReturn(userEntity);

        String jsonUser = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(put("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateUserReturnsHttp400BadRequestWhenAllTheFieldsAreEmpty() throws Exception {
        UserEntity userEntity = anyValidUpdatedUser();
        userEntity.setUsername("");
        userEntity.setPassword("");

        when(userService.isExists(anyLong())).thenReturn(true);
        when(userService.updateUser(anyLong(), any(UserEntity.class))).thenReturn(userEntity);

        String jsonUser = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(put("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testThatDeleteUserSuccessfullyReturnsHttp204NoContentWhenUserExists() throws Exception {
        when(userService.createUser(any(UserEntity.class))).thenReturn(anyValidUser());

        mockMvc.perform(delete("/api/v1/userManagement/users/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteUserReturnsHttp204NoContentWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/userManagement/users/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }


}