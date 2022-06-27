package io.sglo.account.user;

import io.sglo.account.common.entity.User;
import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;
import io.sglo.account.common.repository.UserRepository;
import io.sglo.account.common.validation.UserInfoValidation;
import io.sglo.account.user.dto.UpdatePasswordRequest;
import io.sglo.account.user.dto.UserDeleteRequest;
import io.sglo.account.user.dto.UserInfoResponse;
import io.sglo.account.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse getUserInfo(Long id){
        return new UserInfoResponse(getUserById(id));
    }

    @Transactional
//    @PreAuthorize()
    public void updateUserInfo(Long id, UserUpdateRequest dto) {
        User user = getUserById(id);
        if(dto.realname() != null)
            user.updateRealname(dto.realname());
        if(dto.nickname() != null)
            user.updateNickname(UserInfoValidation.checkNickname(dto.nickname()));
        if(dto.email() != null)
            user.updateEmail(dto.email());
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest dto) {
        User user = getUserById(id);
        if (!passwordEncoder.matches(dto.checkPassword(), user.getPassword())){
            throw new BaseException(UserExceptionType.WRONG_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(dto.newPassword()));
    }

    @Transactional
    public void deleteUser(Long id, UserDeleteRequest dto){
        User user = getUserById(id);
        if (!passwordEncoder.matches(dto.checkPassword(), user.getPassword())){
            throw new BaseException(UserExceptionType.WRONG_PASSWORD);
        }
        userRepository.delete(user);
    }

    //== helper methods ==//
    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
    }

}
