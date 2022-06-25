package io.sglo.account.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sglo.account.auth.dto.LoginRequest;
import io.sglo.account.auth.dto.LoginResponse;
import io.sglo.account.auth.dto.RefreshTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @InjectMocks AuthController authController;
    MockMvc mockMvc;
    @Mock
    AuthService authService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    /**
     * Login Test
     */
    @Test
    void login() throws Exception {
        // given
        LoginRequest req = new LoginRequest("username", "123456a!");
        given(authService.login(req)).willReturn(new LoginResponse("access", "refresh"));

        // when, then
        mockMvc.perform(
                        post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access"))
                .andExpect(jsonPath("$.refreshToken").value("refresh"));

        verify(authService).login(req);
    }

    @Test
    public void loginArgumentNotValidException() throws Exception{
        // then
        mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(" ", "password"))))
                .andExpect(status().isBadRequest());
    }

    /**
     * Refresh Token test
     */
    @Test
    public void refreshToken() throws Exception{
        // given
        given(authService.refreshAccessToken("refreshToken")).willReturn(new RefreshTokenResponse("accessToken"));

        // then
        mockMvc.perform(
                post("/api/refresh-token")
                        .header("Authorization", "refreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"));
    }

}