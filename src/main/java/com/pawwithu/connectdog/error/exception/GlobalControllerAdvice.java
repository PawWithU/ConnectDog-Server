package com.pawwithu.connectdog.error.exception;

import com.pawwithu.connectdog.error.custom.BusinessException;
import com.pawwithu.connectdog.error.custom.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handle(final Exception e) {
        log.error("Internal Error occurred", e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final TokenException e) {
        log.info("Invalid Token: {}", e);
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(final BusinessException e) {
        log.info("businessException: {}", e);
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }

}
