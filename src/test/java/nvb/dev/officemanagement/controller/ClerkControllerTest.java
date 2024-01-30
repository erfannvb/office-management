package nvb.dev.officemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;

import static nvb.dev.officemanagement.MotherObject.anyValidClerk;
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
class ClerkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ClerkService clerkService;

    @Test
    void testThatCreateClerkSuccessfullyReturnsHttp201Created() throws Exception {
        when(clerkService.createClerk(any(ClerkEntity.class), anyLong(), anyLong())).thenReturn(anyValidClerk());

        String jsonClerk = objectMapper.writeValueAsString(anyValidClerk());

        mockMvc.perform(post("/api/v1/clerk/office/1/manager/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonClerk)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatUpdateClerkSuccessfullyReturnsHttp200OkWhenClerkExists() throws Exception {
        when(clerkService.updateClerk(any(ClerkEntity.class), anyLong(), anyLong())).thenReturn(anyValidClerk());
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(anyValidClerk());

        mockMvc.perform(put("/api/v1/clerk/office/1/manager/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonClerk)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void testThatUpdateClerkReturnsHttp404WhenClerkDoesNotExist() throws Exception {
        when(clerkService.updateClerk(any(ClerkEntity.class), anyLong(), anyLong())).thenReturn(anyValidClerk());

        String jsonClerk = objectMapper.writeValueAsString(anyValidClerk());

        mockMvc.perform(put("/api/v1/clerk/office/99/manager/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonClerk)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatFindAllClerksSuccessfullyReturnsHttp200Ok() throws Exception {
        mockMvc.perform(get("/api/v1/clerk/all")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testThatFindClerkByIdReturnsHttp200OkWhenClerkExists() throws Exception {
        when(clerkService.findClerkById(anyLong())).thenReturn(Optional.of(anyValidClerk()));

        mockMvc.perform(get("/api/v1/clerk/" + anyValidClerk().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatFindClerkByIdReturnsHttp404WhenClerkDoesNotExist() throws Exception {
        when(clerkService.findClerkById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/clerk/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatPartialUpdateReturnsSuccessfully200OkWhenClerkExists() throws Exception {
        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(anyValidClerk());
        when(clerkService.isExists(anyLong())).thenReturn(true);

        String jsonClerk = objectMapper.writeValueAsString(anyValidClerk());

        mockMvc.perform(patch("/api/v1/clerk/" + anyValidClerk().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonClerk)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"));
    }

    @Test
    void testThatPartialUpdateReturns404WhenClerkDoesNotExist() throws Exception {
        when(clerkService.partialUpdate(anyLong(), any(ClerkEntity.class))).thenReturn(anyValidClerk());

        String jsonClerk = objectMapper.writeValueAsString(anyValidClerk());

        mockMvc.perform(patch("/api/v1/clerk/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonClerk)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testThatDeleteByIdReturnsHttp204WhenClerkExists() throws Exception {
        when(clerkService.createClerk(any(ClerkEntity.class), anyLong(), anyLong())).thenReturn(anyValidClerk());

        mockMvc.perform(delete("/api/v1/clerk/" + anyValidClerk().getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    void testThatDeleteByIdReturnsHttp204WhenClerkDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/v1/clerk/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}