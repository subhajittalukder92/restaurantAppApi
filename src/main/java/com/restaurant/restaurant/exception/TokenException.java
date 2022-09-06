package com.restaurant.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenException extends RuntimeException {
    private static final long serialVersionUID = 2069320888588831721L;
    private String message;
    public TokenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
