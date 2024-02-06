package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.officemanagement.model.request.AuthRequest;
import nvb.dev.officemanagement.model.request.RefreshTokenRequest;
import nvb.dev.officemanagement.security.AuthService;
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

import static nvb.dev.officemanagement.MotherObject.anyValidJwtAuthResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    @Test
    void testThatAuthenticateSuccessfullyReturnsHttp200Ok() throws Exception {
        when(authService.authenticate(any(AuthRequest.class))).thenReturn(anyValidJwtAuthResponse());

        String jsonJwt = objectMapper.writeValueAsString(anyValidJwtAuthResponse());

        mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonJwt)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatRefreshSuccessfullyReturnsHttp200Ok() throws Exception {
        when(authService.refreshToken(any(RefreshTokenRequest.class))).thenReturn(anyValidJwtAuthResponse());

        String jsonJwt = objectMapper.writeValueAsString(anyValidJwtAuthResponse());

        mockMvc.perform(post("/api/v1/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonJwt)
        ).andExpect(status().isOk());
    }

}