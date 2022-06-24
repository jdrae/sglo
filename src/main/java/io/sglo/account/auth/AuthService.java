package io.sglo.account.auth;

import io.sglo.account.auth.dto.LoginRequest;
import io.sglo.account.auth.dto.LoginResponse;
import io.sglo.account.common.entity.User;
import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest dto) throws BaseException {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BaseException(UserExceptionType.NOT_FOUND_MEMBER));

        // TODO

        return new LoginResponse("access", "refresh");
    }
}
