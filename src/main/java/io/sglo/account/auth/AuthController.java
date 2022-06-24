package io.sglo.account.auth;

import io.sglo.account.auth.dto.LoginRequest;
import io.sglo.account.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(OK)
    public LoginResponse login(@RequestBody LoginRequest dto){
        return authService.login(dto);
    }


}
