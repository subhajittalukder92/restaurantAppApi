package com.restaurant.restaurant.payload.request;

import com.restaurant.restaurant.validation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email
    @UniqueEmail(message = "This email is already taken.")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
}
