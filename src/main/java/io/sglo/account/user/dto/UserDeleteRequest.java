package io.sglo.account.user.dto;

import javax.validation.constraints.NotBlank;

public record UserDeleteRequest (@NotBlank String checkPassword){}
