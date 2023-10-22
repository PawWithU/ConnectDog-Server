package com.pawwithu.connectdog.error.exception.custom;

import com.pawwithu.connectdog.error.ErrorCode;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final String code;

    public TokenException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }


}
