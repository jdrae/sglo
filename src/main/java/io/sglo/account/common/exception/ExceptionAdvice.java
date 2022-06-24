package io.sglo.account.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionDto> baseException(BaseException exception){
        return new ResponseEntity<>(
                new ExceptionDto(exception.getErrorMessage()), exception.getHttpStatus());
    }

    @Data
    @AllArgsConstructor
    class ExceptionDto {
        private String error;
    }
}
