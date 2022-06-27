package io.sglo.account.register;

import io.sglo.account.common.entity.User;
import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.common.repository.UserRepository;
import io.sglo.account.common.validation.UserInfoValidation;
import io.sglo.account.register.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new BaseException(UserExceptionType.DUPLICATED_USERNAME);

        String validNickname = UserInfoValidation.checkNickname(dto.getNickname());
        if(validNickname != null) {
            if (userRepository.existsByNickname(validNickname))
                throw new BaseException(UserExceptionType.DUPLICATED_NICKNAME);
        }

        User user = User.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .realname(dto.getRealname())
                        .nickname(validNickname)
                        .email(dto.getEmail())
                        .build();
        userRepository.save(user);
    }

}
