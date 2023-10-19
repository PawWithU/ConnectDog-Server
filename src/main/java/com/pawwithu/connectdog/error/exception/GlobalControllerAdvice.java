package com.pawwithu.connectdog.error.exception;

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

}
