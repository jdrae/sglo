package io.sglo.account.common.exception;

import org.springframework.http.HttpStatus;

public enum UserExceptionType implements BaseExceptionType{
    LOGIN_FAILURE(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    REFRESH_TOKEN_FAILURE(HttpStatus.BAD_REQUEST, "토큰 검증에 실패했습니다.")
    ;

    private HttpStatus httpStatus;
    private String errorMessage;

    UserExceptionType(HttpStatus httpStatus, String errorMessage){
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() { return this.httpStatus; }

    @Override
    public String getErrorMessage() { return this.errorMessage; }
}
