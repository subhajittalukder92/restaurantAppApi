package com.restaurant.restaurant.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.restaurant.exception.CartEmptyException;
import com.restaurant.restaurant.exception.ResourceNotFoundException;
import com.restaurant.restaurant.exception.TokenException;
import com.restaurant.restaurant.payload.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND,"Resource not found",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST,"Invalid argument",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> err = new HashMap<>();

       List<Map<String,String>> errors = new ArrayList<>();
        //        errors   = ex.getBindingResult().getFieldErrors()
        //                .stream().map(fieldError -> fieldError.getField()+":"+fieldError.getDefaultMessage())
        //                .collect(Collectors.toList());
         ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            err.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        //String json = new ObjectMapper().writeValueAsString(err);
        errors.add(err);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST,"Valodation Errors",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupport(HttpRequestMethodNotSupportedException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED,"Method Not Allowed.",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);

    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledException(DisabledException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,"Disabled User",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,"Wrong Credential",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST,"Message Not readable",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<?> handleTokenException(TokenException ex){
        System.out.println("Here ");
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,"Token problem.",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);

    }
    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<?> handleCartEmptyException(CartEmptyException ex){

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,"Empty cart.",errors );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex){
//        List<String> errors = new ArrayList<>();
//        errors.add(ex.getMessage());
//        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,"Email doen't exist.",errors );
//        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
//    }

}
