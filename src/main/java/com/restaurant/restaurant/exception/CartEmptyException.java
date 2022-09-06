package com.restaurant.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CartEmptyException extends RuntimeException {

    private static final long serialVersionUID = 4495161983542218631L;
    private String message;

    public CartEmptyException(String message) {

        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
