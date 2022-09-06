package com.restaurant.restaurant.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restaurant.restaurant.utils.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<?> errors;
}
