package io.sglo.account.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> exception(Exception e){
        return createExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> accessDeniedException(AccessDeniedException e) {
        return createExceptionResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionDto> bindException(BindException e) {
        return createExceptionResponse(HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getField()+" - " + e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionDto> missingRequestHeaderException(MissingRequestHeaderException e) {
        return createExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //== custom exception ==//
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionDto> baseException(BaseException exception){
        return createExceptionResponse(exception.getHttpStatus(), exception.getErrorMessage());
    }

    //== helper methods ==//
    private ResponseEntity<ExceptionDto> createExceptionResponse(HttpStatus code, String msg){
        return new ResponseEntity<>(new ExceptionDto(msg), code);
    }

    @Data
    @AllArgsConstructor
    class ExceptionDto {
        private String error;
    }
}
