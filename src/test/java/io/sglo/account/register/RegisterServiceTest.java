package io.sglo.account.register;

import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.common.repository.UserRepository;
import io.sglo.account.register.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {
    @InjectMocks
    RegisterService registerService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;

    @Test
    void register() throws Exception{
        // given
        RegisterRequest req = createRegisterRequest();

        // when
        registerService.register(req);
        
        // then
        verify(passwordEncoder).encode(req.getPassword());
        verify(userRepository).save(any());
    }

    @Test
    void registerDuplicateUsername() throws Exception{
        // given
        given(userRepository.existsByUsername(anyString())).willReturn(true);

        // then
        assertThat(assertThrows(BaseException.class, () -> registerService.register(createRegisterRequest())).getExceptionType())
                .isEqualTo(UserExceptionType.DUPLICATED_USERNAME);
    }


    @Test
    void registerDuplicateNickname() throws Exception{
        // given
        given(userRepository.existsByNickname(anyString())).willReturn(true);

        // then
        assertThat(assertThrows(BaseException.class, () -> registerService.register(createRegisterRequest())).getExceptionType())
                .isEqualTo(UserExceptionType.DUPLICATED_NICKNAME);
    }

    //== helper methods ==//
    RegisterRequest createRegisterRequest(){
        return new RegisterRequest("user", "user123!",null,"user",null);
    }
}