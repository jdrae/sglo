package io.sglo.account.user;

import io.sglo.account.user.dto.UpdatePasswordRequest;
import io.sglo.account.user.dto.UserDeleteRequest;
import io.sglo.account.user.dto.UserInfoResponse;
import io.sglo.account.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserInfoResponse getUserInfo(@PathVariable Long id){
        return userService.getUserInfo(id);
    }

    @PutMapping("/{id}")
    public void updateUserInfo(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest dto){
        userService.updateUserInfo(id, dto);
    }

    @PutMapping("/{id}/password")
    public void updatePassword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordRequest dto){
        userService.updatePassword(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, @Valid @RequestBody UserDeleteRequest dto){
        userService.deleteUser(id, dto);
    }
}
