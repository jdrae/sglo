package io.sglo.account.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.ExceptionAdvice;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.register.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {
    @InjectMocks RegisterController registerController;
    @Mock RegisterService registerService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).setControllerAdvice(new ExceptionAdvice()).build();
    }

    @Test
    void register() throws Exception{
        // given
        RegisterRequest req = createRegisterRequest();

        // then
        performRegister(req)
                .andExpect(status().isCreated());

        verify(registerService).register(req);
    }

    @Test
    void registerDuplicatedUsernameException() throws Exception{
        // given
        RegisterRequest req = createRegisterRequest();
        doThrow(new BaseException(UserExceptionType.DUPLICATED_USERNAME)).when(registerService).register(any());

        // then
        performRegister(req)
                .andExpect(status().isConflict());
    }

    @Test
    void registerDuplicatedNicknameException() throws Exception{
        // given
        RegisterRequest req = createRegisterRequest();
        doThrow(new BaseException(UserExceptionType.DUPLICATED_NICKNAME)).when(registerService).register(any());

        // then
        performRegister(req)
                .andExpect(status().isConflict());
    }

    @Test
    void registerArgumentNotValidException() throws Exception{
        // given
        RegisterRequest req = new RegisterRequest("","",null,null,null);

        performRegister(req)
                .andExpect(status().isBadRequest());
    }

    //== helper methods ==//
    private ResultActions performRegister(RegisterRequest req) throws Exception {
        return mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)));
    }


    RegisterRequest createRegisterRequest(){
        return new RegisterRequest("user", "user123!",null,"user",null);
    }

}