package io.sglo.account.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min=2, max = 30) String realname,
        @Size(min=3, max = 30) String nickname,
        @Email String email){}