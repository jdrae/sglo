package io.sglo.account.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Schema(example = "user")
    @NotBlank
    private String username;

    @Schema(example = "user123!")
    @NotBlank
    private String password;

}
