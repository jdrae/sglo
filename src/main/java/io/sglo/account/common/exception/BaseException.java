package io.sglo.account.common.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

    private BaseExceptionType baseExceptionType;

    public BaseException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    public BaseExceptionType getExceptionType(){
        return this.baseExceptionType;
    };

    public HttpStatus getHttpStatus(){ return baseExceptionType.getHttpStatus(); }
    public String getErrorMessage(){ return baseExceptionType.getErrorMessage(); }
}

