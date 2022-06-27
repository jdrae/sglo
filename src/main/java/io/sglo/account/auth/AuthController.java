package io.sglo.account.auth;

import io.sglo.account.auth.dto.LoginRequest;
import io.sglo.account.auth.dto.LoginResponse;
import io.sglo.account.auth.dto.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest dto){
        return authService.login(dto);
    }

    // TODO: 삭제된 유저가 재발급 받으려고 하면?
    // TODO: move logic to filter chain
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public RefreshTokenResponse refreshToken(@Parameter(hidden = true) @RequestHeader(value = "Authorization") String refreshToken){
        return authService.refreshAccessToken(refreshToken);
    }


}
