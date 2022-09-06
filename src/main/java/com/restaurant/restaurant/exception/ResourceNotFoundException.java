package com.restaurant.restaurant.exception;

import com.restaurant.restaurant.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -6109410620913524010L;
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    private String message;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super();
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        setMessage();
    }
    public String getResourseName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage() {
        this.message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);
    }
}
