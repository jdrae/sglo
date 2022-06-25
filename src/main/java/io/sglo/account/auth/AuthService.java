package io.sglo.account.auth;

import io.sglo.account.auth.dto.LoginRequest;
import io.sglo.account.auth.dto.LoginResponse;
import io.sglo.account.auth.dto.RefreshTokenResponse;
import io.sglo.account.common.entity.User;
import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.common.repository.UserRepository;
import io.sglo.account.jwt.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenHelper accessTokenHelper;
    private final TokenHelper refreshTokenHelper;


    public LoginResponse login(LoginRequest dto) throws BaseException {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BaseException(UserExceptionType.LOGIN_FAILURE));
        validatePassword(dto, user);
        TokenHelper.PrivateClaims privateClaims = createPrivateClaims(user);
        String accessToken = accessTokenHelper.createToken(privateClaims);
        String refreshToken = refreshTokenHelper.createToken(privateClaims);
        // TODO: set token in header
        return new LoginResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        TokenHelper.PrivateClaims privateClaims = refreshTokenHelper.parse(refreshToken)
                .orElseThrow(() -> new BaseException(UserExceptionType.REFRESH_TOKEN_FAILURE));

        String accessToken = accessTokenHelper.createToken(privateClaims);
        return new RefreshTokenResponse(accessToken);
    }

    //== helper methods ==//
    private void validatePassword(LoginRequest dto, User user){
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new BaseException(UserExceptionType.LOGIN_FAILURE);
        }
    }

    private TokenHelper.PrivateClaims createPrivateClaims(User user){
        return new TokenHelper.PrivateClaims(
                String.valueOf(user.getId()));
    }

}
