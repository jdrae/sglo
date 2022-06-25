package io.sglo.account.auth;

import io.sglo.account.auth.dto.LoginRequest;
import io.sglo.account.auth.dto.LoginResponse;
import io.sglo.account.common.entity.User;
import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.common.repository.UserRepository;
import io.sglo.account.jwt.TokenHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    AuthService authService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    TokenHelper accessTokenHelper;
    @Mock
    TokenHelper refreshTokenHelper;

    @BeforeEach
    void beforeEach(){
        authService = new AuthService(userRepository, passwordEncoder, accessTokenHelper, refreshTokenHelper);
    }

    @Test
    public void login() throws Exception{
        // given
        given(userRepository.findByUsername(any())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(accessTokenHelper.createToken(any())).willReturn("access");
        given(refreshTokenHelper.createToken(any())).willReturn("refresh");

        // when
        LoginResponse res = authService.login(new LoginRequest("username", "password"));

        // then
        assertThat(res.getAccessToken()).isEqualTo("access");
        assertThat(res.getRefreshToken()).isEqualTo("refresh");
    }

    @Test
    public void loginExceptionByUserNotFound() throws Exception{
        // given
        given(userRepository.findByUsername(any())).willReturn(Optional.empty());

        // then
        assertThat(assertThrows(BaseException.class, () -> authService.login(new LoginRequest("user","pass"))).getExceptionType())
                .isEqualTo(UserExceptionType.LOGIN_FAILURE);
    }

    @Test
    public void loginExceptionByInvalidPassword() throws Exception{
        // given
        given(userRepository.findByUsername(any())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // then
        assertThat(assertThrows(BaseException.class, () -> authService.login(new LoginRequest("user","pass"))).getExceptionType())
                .isEqualTo(UserExceptionType.LOGIN_FAILURE);
    }

    //== helper methods==//
    public static User createMember() {
        return new User("user",  "123456a!");
    }
}