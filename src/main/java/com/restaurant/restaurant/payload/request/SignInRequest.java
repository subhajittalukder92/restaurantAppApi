package com.restaurant.restaurant.payload.request;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class SignInRequest implements Serializable {

    private static final long serialVersionUID = -3904194995964511408L;
    @NotEmpty(message = "User name is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String Password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
